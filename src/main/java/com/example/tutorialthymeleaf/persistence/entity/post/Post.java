package com.example.tutorialthymeleaf.persistence.entity.post;

import com.example.tutorialthymeleaf.persistence.entity.AbstractEntity;
import com.example.tutorialthymeleaf.persistence.entity.user.Personal;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Iehor Funtusov, created 31/12/2020 - 12:45 PM
 */

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post extends AbstractEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Personal personal;

    public Post() {
        super();
    }
}
