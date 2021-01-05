package com.example.tutorialthymeleaf.persistence.entity.user;

import com.example.tutorialthymeleaf.persistence.listener.AgeByBirthDayGenerationListener;
import com.example.tutorialthymeleaf.persistence.listener.FullNameGenerationListener;
import com.example.tutorialthymeleaf.persistence.type.GenderType;
import com.example.tutorialthymeleaf.persistence.type.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:27 AM
 */

@Getter
@Setter
@Entity
@DiscriminatorValue("PERSONAL")
@EntityListeners({
        FullNameGenerationListener.class,
        AgeByBirthDayGenerationListener.class
})
public class Personal extends User {

    @Column(name = "GENDER_TYPE")
    @Enumerated(value = EnumType.STRING)
    private GenderType genderType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BIRTH_DAY")
    private Date birthDay;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Transient
    private String fullName;

    @Transient
    private Integer age;

    public Personal() {
        super();
        setRoleType(RoleType.ROLE_PERSONAL);
    }
}
