package com.example.service;

import com.candidatetestspecification.dtos.product.ProductRequestDto;
import com.candidatetestspecification.dtos.product.ProductResponseDto;
import com.candidatetestspecification.entities.Product;
import com.candidatetestspecification.repository.ProductRepository;
import com.candidatetestspecification.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() throws ParseException {
        String dateString = "03-01-2023";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse(dateString);
        Product product = new Product();
        ProductResponseDto productResponseDto = new ProductResponseDto();
        ProductRequestDto productRequestDto = new ProductRequestDto(date,
                "11111",
                "Test Inventory 1",
                20L,
                "Paid");

        Mockito.when(modelMapper.map(productRequestDto, Product.class)).thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductResponseDto.class)).thenReturn(productResponseDto);
        Mockito.when(productRepository.save(product)).thenReturn(product);

        ProductResponseDto response = productService.save(productRequestDto);

        Assertions.assertNotNull(response);

        Mockito.verify(modelMapper).map(productRequestDto, Product.class);
        Mockito.verify(productRepository).save(product);
        Mockito.verify(modelMapper).map(product, ProductResponseDto.class);
    }

    @Test
    void getAllTest() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        List<ProductResponseDto> productResponseDtos = products.stream()
                .map(product -> new ProductResponseDto())
                .collect(Collectors.toList());

        Mockito.when(productRepository.findAll()).thenReturn(products);
        for (int i = 0; i < products.size(); i++) {
            Mockito.when(modelMapper.map(products.get(i), ProductResponseDto.class))
                    .thenReturn(productResponseDtos.get(i));
        }

        Map<String, Object> response = productService.getAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals("products", response.get("table"));
        Assertions.assertTrue(response.get("records") instanceof List<?>);
        List<ProductResponseDto> responseProducts = (List<ProductResponseDto>) response.get("records");
        Assertions.assertEquals(productResponseDtos.size(), responseProducts.size());
        Mockito.verify(productRepository).findAll();
        products.forEach(product -> Mockito.verify(modelMapper).map(product, ProductResponseDto.class));
    }
}
