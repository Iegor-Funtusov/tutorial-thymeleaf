package com.example.tutorialthymeleaf.facade;

import com.example.tutorialthymeleaf.web.data.request.PersonalData;
import com.example.tutorialthymeleaf.web.data.response.PageData;

import org.springframework.web.context.request.WebRequest;

/**
 * @author Iehor Funtusov, created 04/01/2021 - 5:30 AM
 */

public interface PersonalAdminFacade {

    PageData<PersonalData> findAll(WebRequest request);
    PersonalData findById(Integer id);
    void delete(Integer id);
    void lockAccount(Integer id);
    void unlockAccount(Integer id);
}
