package com.example.tutorialthymeleaf.persistence.entity.post;

import com.example.tutorialthymeleaf.persistence.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 12:20 PM
 */

@Getter
@Setter
@Entity
@Table(name = "reactions")
public class Reaction extends AbstractEntity {

    @Column(name = "personal_id", nullable = false)
    private Integer personalId;

    @Column(name = "post_id", nullable = false)
    private Integer postId;

    @Column(name = "like_post", nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean like;

    public Reaction() {
        super();
        this.like = false;
    }
}
