package com.example.tutorialthymeleaf.web.data.response;

import com.example.tutorialthymeleaf.persistence.entity.post.Post;
import com.example.tutorialthymeleaf.web.data.request.PostData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 2:09 PM
 */

@Getter
@Setter
@ToString
public class PostResponseData extends PostData {

    private Map<Integer, String> likes;
    private Map<Integer, String> dislikes;

    public PostResponseData(Post post) {
        super(post);
        this.likes = new HashMap<>();
        this.dislikes = new HashMap<>();
    }
}
