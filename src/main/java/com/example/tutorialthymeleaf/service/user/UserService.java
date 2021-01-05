package com.example.tutorialthymeleaf.service.user;

import com.example.tutorialthymeleaf.persistence.entity.user.User;
import com.example.tutorialthymeleaf.service.CrudService;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:51 AM
 */

public interface UserService<U extends User> extends CrudService<U> { }
