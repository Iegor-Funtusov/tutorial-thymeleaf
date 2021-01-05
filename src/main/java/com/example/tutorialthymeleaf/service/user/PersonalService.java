package com.example.tutorialthymeleaf.service.user;

import com.example.tutorialthymeleaf.persistence.entity.user.Personal;

import java.util.List;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:57 AM
 */
public interface PersonalService extends UserService<Personal> {

    Personal findByEmail(String email);
    List<Personal> findAllByListId(List<Integer> ids);
}
