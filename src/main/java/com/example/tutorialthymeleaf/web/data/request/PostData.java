package com.example.tutorialthymeleaf.web.data.request;

import com.example.tutorialthymeleaf.persistence.entity.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 12:33 PM
 */

@Getter
@Setter
@NoArgsConstructor
public class PostData {

    private Integer id;
    private String title;
    private String message;
    private String comment;
    private Date created;

    public PostData(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.comment = post.getComment();
        this.message = post.getMessage();
        this.created = post.getCreated();
    }
}
