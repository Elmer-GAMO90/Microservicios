package com.tecsup.app.micro.product.service;

import com.tecsup.app.micro.product.client.User;
import com.tecsup.app.micro.product.client.UserClient;
import com.tecsup.app.micro.product.dto.Product;
import com.tecsup.app.micro.product.dto.ProductRequest;
import com.tecsup.app.micro.product.entity.ProductEntity;
import com.tecsup.app.micro.product.mapper.ProductMapper;
import com.tecsup.app.micro.product.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    // 🔹 Crear producto
    public Product createProduct(ProductRequest request) {

        // 1️⃣ Verificar si el usuario existe en el microservicio de User
        User user;
        try {
            user = userClient.getUserById(request.getCreatedBy());
        } catch (Exception e) {
            log.error("No se pudo validar el usuario con id {}", request.getCreatedBy());
            throw new RuntimeException("El usuario no existe en el sistema");
        }
        ProductEntity productEntity = mapper.toEntity(request);
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());

        // 2️⃣ Mapear a entidad y guardar en la base de datos
    /*    ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
        productEntity.setPrice(request.getPrice());
        productEntity.setStock(request.getStock());
        productEntity.setCategory(request.getCategory());
        productEntity.setCreatedBy(request.getCreatedBy());
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());
*/
        //var productoEntity= mapper.toEntity(request);

        ProductEntity savedEntity = productRepository.save(productEntity);

        log.info("Producto guardado correctamente: {}", savedEntity);

        // 3️⃣ Retornar el producto con la información del usuario creador
        return mapper.toDomainWithUser(savedEntity, user);

    }

    public List<Product> getAllProducts() {
        List<ProductEntity> entities = productRepository.findAll();

        /*ProductEntity productEntity = productRepository.findById(id).orElse(null);

        User user = userClient.getUserById(productEntity.getCreatedBy());
        log.info("User : {}", user);*/

        return this.mapper.toDomain(entities);
    }

}
