package com.example.tutorialthymeleaf.facade.impl;

import com.example.tutorialthymeleaf.facade.RegistrationFacade;
import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import com.example.tutorialthymeleaf.service.user.PersonalService;
import com.example.tutorialthymeleaf.web.data.request.AuthData;

import org.springframework.stereotype.Service;

/**
 * @author Iehor Funtusov, created 31/12/2020 - 10:01 AM
 */
@Service
public class RegistrationFacadeImpl implements RegistrationFacade {

    private final PersonalService personalService;

    public RegistrationFacadeImpl(PersonalService personalService) {
        this.personalService = personalService;
    }

    @Override
    public void registration(AuthData authData) {
        Personal personal = new Personal();
        personal.setEmail(authData.getEmail());
        personal.setPassword(authData.getPassword());
        personalService.create(personal);
    }
}
