package com.example.tutorialthymeleaf.web.data.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iehor Funtusov, created 24/12/2020 - 10:33 AM
 */

@Getter
@Setter
public class AuthData {

    private String email;
    private String password;
    private String passwordConfirm;
}
