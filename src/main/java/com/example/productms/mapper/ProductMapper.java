package com.example.productms.mapper;

import com.example.productms.dto.ProductDto;
import com.example.productms.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(ProductEntity productEntity) {
        return ProductDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .count(productEntity.getCount()).build();

    }

    public ProductEntity toEntity(ProductDto dto) {
        return ProductEntity.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .count(dto.getCount()).build();

    }
}
