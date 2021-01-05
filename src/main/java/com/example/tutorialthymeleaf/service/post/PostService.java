package com.example.tutorialthymeleaf.service.post;

import com.example.tutorialthymeleaf.persistence.entity.post.Post;
import com.example.tutorialthymeleaf.service.CrudService;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 12:25 PM
 */
public interface PostService extends CrudService<Post> {

    void like(Integer id);
    void dislike(Integer id);
    void uploadFile(MultipartFile file, Integer postId);
}
