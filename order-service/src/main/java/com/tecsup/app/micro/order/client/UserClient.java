package com.tecsup.app.micro.order.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class UserClient {

    //Esta clase me va a permitir comunicarme con el microservicio User
    //Tbn hay que definirlo como un @bean
    //Busqueda de un objeto
    private final RestTemplate restTemplate;

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Definir parámetros de configuración
    @Value("${user.service.url}") //Se define en el properties
    private String userServiceUrl; // = "http://localhost:8081"

    @CircuitBreaker(name = "userService",
            fallbackMethod = "getUserByIdFallback")

    public User getUserById(Long createdBy) {

        String url = userServiceUrl + "/api/users/" + createdBy;

        User usr = restTemplate.getForObject(url, User.class);
        log.info("User retrieved successfully from userdb: {}", usr);
        return usr;
    }
    private User getUserByIdFallback(Long createdBy, Throwable throwable) {
        log.warn("Fallback method invoked for getUserById due to: {}", throwable.getMessage());
        return User.builder()
                .id(createdBy)
                .name("Unknown User")
                .email("Unknown Email")
                .phone("Unknown Phone")
                .address("Unknown Address")
                .build();
    }

    //Aqui usamos el @Builder, nos permite generar data estática, nos ayuda con el constructor

        /*return User.builder()
                .name("John Doe")
                .build();*/


}
