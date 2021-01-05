package com.example.tutorialthymeleaf.persistence.entity.user;

import com.example.tutorialthymeleaf.persistence.type.RoleType;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:26 AM
 */

@Getter
@Setter
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin() {
        super();
        setRoleType(RoleType.ROLE_ADMIN);
    }
}
