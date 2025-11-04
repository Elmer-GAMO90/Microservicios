package com.tecsup.app.micro.order.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ProductClient {

    //Esta clase me va a permitir comunicarme con el microservicio Product
    //Tbn hay que definirlo como un @bean
    //Busqueda de un objeto
    private final RestTemplate restTemplate;
    //Se debe instanciar o generar el bean, para ello usamos el @Bean y no este tipo de instanciación
    //private final RestTemplate restTemplate = new RestTemplate();

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Definir parámetros de configuración
    @Value("${product.service.url}") //Se define en el properties
    private String productServiceUrl; // = "http://localhost:8082"

    @CircuitBreaker(name = "productService",
            fallbackMethod = "getProductByIdFallback")

    public Product getProductById(Long createdBy) {

        String url = productServiceUrl + "/api/products/" + createdBy;


        Product product = restTemplate.getForObject(url, Product.class);
        log.info("Product retrieved successfully from productdb: {}", product);
        return product;
    }
    private Product getProductByIdFallback(Long createdBy, Throwable throwable) {
        log.warn("Fallback method invoked for getProductById due to: {}", throwable.getMessage());
        return Product.builder()
                .id(createdBy)
                .name("Unknown name")
                .description("Unknown description")
                .price(BigDecimal.ZERO)
                .stock(0)
                .category("Unknown category")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
