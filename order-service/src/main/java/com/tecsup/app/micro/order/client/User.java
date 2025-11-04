package com.tecsup.app.micro.order.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//Esta clase User se va a comunicar con el microservicio user-service
public class User {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
