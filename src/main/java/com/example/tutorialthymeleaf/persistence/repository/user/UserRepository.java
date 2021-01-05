package com.example.tutorialthymeleaf.persistence.repository.user;

import com.example.tutorialthymeleaf.persistence.entity.user.User;
import com.example.tutorialthymeleaf.persistence.repository.AbstractRepository;

import org.springframework.stereotype.Repository;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:31 AM
 */

@Repository
public interface UserRepository<U extends User> extends AbstractRepository<U> {

    U findByEmail(String email);
    boolean existsByEmail(String email);
}
