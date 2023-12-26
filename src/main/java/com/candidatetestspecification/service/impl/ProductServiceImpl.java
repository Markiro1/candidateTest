package com.candidatetestspecification.service.impl;

import com.candidatetestspecification.dtos.product.ProductRequestDto;
import com.candidatetestspecification.dtos.product.ProductResponseDto;
import com.candidatetestspecification.entities.Product;
import com.candidatetestspecification.repository.ProductRepository;
import com.candidatetestspecification.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepo;

    @Override
    public ProductResponseDto save(ProductRequestDto productRequestDto) {
        Product product = modelMapper.map(productRequestDto, Product.class);
        return modelMapper.map(productRepo.save(product), ProductResponseDto.class);
    }

    @Override
    public Map<String, Object> getAll() {
        List<ProductResponseDto> products = productRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("table", "products");
        response.put("records", products);
        return response;
    }

    private ProductResponseDto convertToDto(Product product) {
        return modelMapper.map(product, ProductResponseDto.class);
    }
}
