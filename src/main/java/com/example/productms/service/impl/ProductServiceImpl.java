package com.example.productms.service.impl;

import com.example.productms.dto.ProductDto;
import com.example.productms.entity.ProductEntity;
import com.example.productms.exception.ResourceNotFoundException;
import com.example.productms.mapper.ProductMapper;
import com.example.productms.repository.ProductRepository;
import com.example.productms.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        logger.info("Creating product with details: {}", productDto);
        ProductEntity productEntity = productMapper.toEntity(productDto);
        ProductEntity savedProduct = productRepository.save(productEntity);
        logger.info("Product created successfully with ID: {}", savedProduct.getId());
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new ResourceNotFoundException("Product not found with id: " + id);
                });
        logger.info("Product found with ID: {}", id);
        return productMapper.toDto(productEntity);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        logger.info("Fetching all products");
        List<ProductDto> products = productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Total products fetched: {}", products.size());
        return products;
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        logger.info("Updating product with ID: {}", id);
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new ResourceNotFoundException("Product not found with id: " + id);
                });

        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setCount(productDto.getCount());
        ProductEntity updatedProduct = productRepository.save(existingProduct);
        logger.info("Product updated successfully with ID: {}", id);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new ResourceNotFoundException("Product not found with id: " + id);
                });
        productRepository.delete(product);
        logger.info("Product deleted successfully with ID: {}", id);
    }
}
