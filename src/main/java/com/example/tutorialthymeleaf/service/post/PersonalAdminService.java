package com.example.tutorialthymeleaf.service.post;

import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import com.example.tutorialthymeleaf.service.CrudService;

/**
 * @author Iehor Funtusov, created 03/01/2021 - 8:30 AM
 */
public interface PersonalAdminService extends CrudService<Personal> {

    void lockAccount(Integer id);
    void unlockAccount(Integer id);
}
