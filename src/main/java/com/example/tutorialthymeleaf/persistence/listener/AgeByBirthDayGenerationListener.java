package com.example.tutorialthymeleaf.persistence.listener;

import com.example.tutorialthymeleaf.persistence.entity.user.Personal;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:29 AM
 */

public class AgeByBirthDayGenerationListener {

    @PostLoad
    @PostPersist
    @PostUpdate
    public void generateAgeByBirthDay(Personal user) {
        if (user.getBirthDay() == null) {
            user.setAge(null);
            return;
        }
        Years years = Years.yearsBetween(new LocalDate(user.getBirthDay()), new LocalDate());
        user.setAge(years.getYears());
    }
}
