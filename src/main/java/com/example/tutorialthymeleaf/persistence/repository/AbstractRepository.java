package com.example.tutorialthymeleaf.persistence.repository;

import com.example.tutorialthymeleaf.persistence.entity.AbstractEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:30 AM
 */

@NoRepositoryBean
public interface AbstractRepository<E extends AbstractEntity> extends JpaRepository<E, Integer>, JpaSpecificationExecutor<E> {

    @Query("select min (ae.created) from #{#entityName} ae")
    Date findMinCreated();

    @Query("select max (ae.created) from #{#entityName} ae")
    Date findMaxCreated();

    Long countAllByCreatedBetween(Date start, Date end);
}
