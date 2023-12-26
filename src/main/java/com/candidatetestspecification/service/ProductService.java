package com.candidatetestspecification.service;

import com.candidatetestspecification.dtos.product.ProductRequestDto;
import com.candidatetestspecification.dtos.product.ProductResponseDto;

import java.util.Map;

public interface ProductService {

    ProductResponseDto save(ProductRequestDto productRequestDto);
    Map<String, Object> getAll();
}
