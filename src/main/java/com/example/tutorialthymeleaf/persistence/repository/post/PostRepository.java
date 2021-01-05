package com.example.tutorialthymeleaf.persistence.repository.post;

import com.example.tutorialthymeleaf.persistence.entity.post.Post;
import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import com.example.tutorialthymeleaf.persistence.repository.AbstractRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 12:21 PM
 */

@Repository
public interface PostRepository extends AbstractRepository<Post> {

    List<Post> findAllByPersonal(Personal personal);
    Page<Post> findAllByPersonal(Personal personal, Pageable pageable);
    Page<Post> findAllByPersonalIsNot(Personal personal, Pageable pageable);
}
