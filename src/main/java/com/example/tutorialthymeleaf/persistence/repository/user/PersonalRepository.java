package com.example.tutorialthymeleaf.persistence.repository.user;

import com.example.tutorialthymeleaf.persistence.entity.user.Personal;

import org.springframework.stereotype.Repository;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:32 AM
 */

@Repository
public interface PersonalRepository extends UserRepository<Personal> { }
