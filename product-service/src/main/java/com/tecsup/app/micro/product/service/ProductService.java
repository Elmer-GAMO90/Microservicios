package com.tecsup.app.micro.product.service;

import com.tecsup.app.micro.product.client.User;
import com.tecsup.app.micro.product.client.UserClient;
import com.tecsup.app.micro.product.dto.Product;
import com.tecsup.app.micro.product.entity.ProductEntity;
import com.tecsup.app.micro.product.mapper.ProductMapper;
import com.tecsup.app.micro.product.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    private final UserClient userClient;

    //Constructor
    public ProductService(ProductRepository productRepository, ProductMapper mapper, UserClient userClient) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.userClient = userClient;
    }

    public Product getProductById(Long id){

        // Call PostgreSQL productdb
        ProductEntity productEntity = productRepository.findById(id).orElse(null);

        //Get client by id
        //log.info("User id: {}", entity.getCreatedBy());

        //Call microservice user
        User user = userClient.getUserById(productEntity.getCreatedBy());
        log.info("User : {}", user);
        //log.info("User name : {}", user.getName());

        //User user = UserClient.getUserById(product)

        return mapper.toDomainWithUser(productEntity, user);

    }

    //Crear un producto y verificar el id del usuario que va a crear exista, si no existe generar una excepcion y si existe grabar el producto
    //Verificar si el usuario existe llamando el microservicio de user
}
