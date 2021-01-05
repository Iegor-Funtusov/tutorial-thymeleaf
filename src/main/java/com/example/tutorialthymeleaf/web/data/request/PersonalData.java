package com.example.tutorialthymeleaf.web.data.request;

import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import com.example.tutorialthymeleaf.persistence.type.GenderType;
import com.example.tutorialthymeleaf.web.data.AbstractEntityData;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Iehor Funtusov, created 24/12/2020 - 10:25 AM
 */

@Getter
@Setter
public class PersonalData extends AbstractEntityData {

    private Date created;
    private Date updated;
    private String email;
    private Boolean enabled;
    private GenderType genderType;
    private Date birthDay;
    private String firstName;
    private String lastName;
    private String fullName;
    private Integer age;

    public PersonalData(Personal personal) {
        setId(personal.getId());
        this.created = personal.getCreated();
        this.updated = personal.getUpdated();
        this.email = personal.getEmail();
        this.enabled = personal.getEnabled();
        this.genderType = personal.getGenderType();
        this.birthDay = personal.getBirthDay();
        this.fullName = personal.getFullName();
        this.age = personal.getAge();
    }

    public PersonalData() {}
}
