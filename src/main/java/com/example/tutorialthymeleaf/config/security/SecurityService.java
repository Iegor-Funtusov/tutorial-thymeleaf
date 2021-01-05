package com.example.tutorialthymeleaf.config.security;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:42 AM
 */

public interface SecurityService {

    boolean isAuthenticated();
    void autoLogin(String username, String password);
}
