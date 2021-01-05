package com.example.tutorialthymeleaf.persistence.listener;

import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import org.apache.commons.lang.StringUtils;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:28 AM
 */

public class FullNameGenerationListener {

    @PostLoad
    @PostPersist
    @PostUpdate
    public void generateFullName(Personal personal) {
        if (StringUtils.isBlank(personal.getFirstName()) || StringUtils.isBlank(personal.getLastName())) {
            personal.setFullName("");
            return;
        }
        personal.setFullName(personal.getFirstName() + " " + personal.getLastName());
    }
}
