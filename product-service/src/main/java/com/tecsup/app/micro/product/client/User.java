package com.tecsup.app.micro.product.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder //(Para generar data, )
public class User {



    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
