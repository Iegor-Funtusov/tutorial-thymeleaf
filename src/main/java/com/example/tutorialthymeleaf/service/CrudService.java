package com.example.tutorialthymeleaf.service;

import com.example.tutorialthymeleaf.persistence.data.PersistenceRequestData;
import com.example.tutorialthymeleaf.persistence.entity.AbstractEntity;

import org.springframework.data.domain.Page;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:50 AM
 */

public interface CrudService<AE extends AbstractEntity> {

    Page<AE> findAll(PersistenceRequestData persistenceRequestData);
    AE findById(Integer id);
    void create(AE ae);
    void update(AE ae);
    void delete(Integer id);
}
