package com.example.tutorialthymeleaf.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Iehor Funtusov, created 29/12/2020 - 8:25 AM
 */

@Getter
@Setter
@MappedSuperclass
public class AbstractEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", nullable = false, updatable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_TIME", nullable = false)
    private Date updated;

    public AbstractEntity() {
        this.created = new Date();
        this.updated = new Date();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updated = new Date();
    }
}
