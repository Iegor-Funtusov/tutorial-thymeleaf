package com.example.tutorialthymeleaf.service.post.impl;

import com.example.tutorialthymeleaf.persistence.data.PersistenceRequestData;
import com.example.tutorialthymeleaf.persistence.entity.post.Post;
import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import com.example.tutorialthymeleaf.persistence.repository.post.PostRepository;
import com.example.tutorialthymeleaf.persistence.repository.user.PersonalRepository;
import com.example.tutorialthymeleaf.service.post.PostService;
import com.example.tutorialthymeleaf.service.post.ReactionService;
import com.example.tutorialthymeleaf.util.SecurityUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 12:25 PM
 */

@Service
public class PostServiceImpl implements PostService {

    private final String UPLOAD_DIR = "./uploads/";

    private final PersonalRepository personalRepository;
    private final PostRepository postRepository;
    private final ReactionService reactionService;

    public PostServiceImpl(PersonalRepository personalRepository, PostRepository postRepository, ReactionService reactionService) {
        this.personalRepository = personalRepository;
        this.postRepository = postRepository;
        this.reactionService = reactionService;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(readOnly = true)
    public Page<Post> findAll(PersistenceRequestData data) {
        Personal personal = personalRepository.findByEmail(SecurityUtil.getUsername());
        Sort sort = data.getOrder().equals("desc") ? Sort.by(Sort.Order.desc(data.getSort())) : Sort.by(Sort.Order.asc(data.getSort()));
        if (data.isOwner()) {
            return postRepository.findAllByPersonal(personal, PageRequest.of(data.getPage() - 1, data.getSize(), sort));
        } else {
            return postRepository.findAllByPersonalIsNot(personal, PageRequest.of(data.getPage() - 1, data.getSize(), sort));
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(readOnly = true)
    public Post findById(Integer id) {
        Post post = postRepository.findById(id).orElse(null);
        existPost(post);
        return post;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void create(Post post) {
        Personal personal = personalRepository.findByEmail(SecurityUtil.getUsername());
        post.setPersonal(personal);
        postRepository.save(post);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void update(Post post) {
        Personal personal = personalRepository.findByEmail(SecurityUtil.getUsername());
        Post current = postRepository.findById(post.getId()).orElse(null);
        validPost(post, personal.getId());
        current.setTitle(post.getTitle());
        current.setMessage(post.getMessage());
        current.setComment(post.getComment());
        postRepository.save(current);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void delete(Integer id) {
        Personal personal = personalRepository.findByEmail(SecurityUtil.getUsername());
        Post post = postRepository.findById(id).orElse(null);
        validPost(post, personal.getId());
        postRepository.delete(post);
        reactionService.deleteByPostId(post.getId());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void like(Integer id) {
        reactionProcess(id, true);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void dislike(Integer id) {
        reactionProcess(id, false);
    }

    @Override
    public void uploadFile(MultipartFile file, Integer postId) {
        String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void validPost(Post post, Integer personalId) {
        existPost(post);
        if (!post.getPersonal().getId().equals(personalId)) {
            throw new RuntimeException("you are not a owner");
        }
        if (StringUtils.isBlank(post.getMessage()) || StringUtils.isBlank(post.getTitle())) {
            throw new RuntimeException("message or title can not be empty");
        }
    }

    private void existPost(Post post) {
        if (post == null) {
            throw new RuntimeException("post not found");
        }
    }

    private void reactionProcess(Integer postId, boolean status) {
        Personal personal = personalRepository.findByEmail(SecurityUtil.getUsername());
        Post post = postRepository.findById(postId).orElse(null);
        hasReactionToPost(post, personal.getId());
        if (status) {
            reactionService.like(post.getId(), personal.getId());
        } else {
            reactionService.dislike(post.getId(), personal.getId());
        }
    }

    private void hasReactionToPost(Post post, Integer personalId) {
        existPost(post);
        if (post.getPersonal().getId().equals(personalId)) {
            throw new RuntimeException("you do not have a reaction to this post");
        }
    }
}
