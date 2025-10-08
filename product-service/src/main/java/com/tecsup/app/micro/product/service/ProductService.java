package com.tecsup.app.micro.product.service;

import com.tecsup.app.micro.product.dto.Product;
import com.tecsup.app.micro.product.entity.ProductEntity;
import com.tecsup.app.micro.product.mapper.ProductMapper;
import com.tecsup.app.micro.product.repository.ProductRepository;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    //Constructor
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Product getProductById(Long id){

        ProductEntity entity = productRepository.findById(id).orElse(null);

        return productMapper.toDomain(entity);

    }
}
