package com.example.tutorialthymeleaf.persistence.repository.user;

import com.example.tutorialthymeleaf.persistence.entity.user.Admin;
import org.springframework.stereotype.Repository;

/**
 * @author Iehor Funtusov, created 31/12/2020 - 10:24 AM
 */

@Repository
public interface AdminRepository extends UserRepository<Admin> { }
