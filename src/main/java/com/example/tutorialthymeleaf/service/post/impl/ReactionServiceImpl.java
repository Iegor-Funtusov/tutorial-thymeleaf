package com.example.tutorialthymeleaf.service.post.impl;

import com.example.tutorialthymeleaf.persistence.entity.post.Post;
import com.example.tutorialthymeleaf.persistence.entity.post.Reaction;
import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import com.example.tutorialthymeleaf.persistence.repository.post.PostRepository;
import com.example.tutorialthymeleaf.persistence.repository.post.ReactionRepository;
import com.example.tutorialthymeleaf.persistence.repository.user.PersonalRepository;
import com.example.tutorialthymeleaf.service.post.ReactionService;
import com.example.tutorialthymeleaf.util.PostUtil;
import com.example.tutorialthymeleaf.util.SecurityUtil;
import com.example.tutorialthymeleaf.web.data.KeyValueData;

import org.apache.commons.collections.CollectionUtils;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 12:31 PM
 */

@Service
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final PersonalRepository personalRepository;
    private final PostRepository postRepository;

    public ReactionServiceImpl(ReactionRepository reactionRepository, PersonalRepository personalRepository, PostRepository postRepository) {
        this.reactionRepository = reactionRepository;
        this.personalRepository = personalRepository;
        this.postRepository = postRepository;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void deleteByPostId(Integer postId) {
        List<Reaction> reactions = reactionRepository.findAllByPostId(postId);
        if (CollectionUtils.isNotEmpty(reactions)) {
            reactionRepository.deleteAll(reactions);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void like(Integer postId, Integer personalId) {
        reactionProcess(postId, personalId, true);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void dislike(Integer postId, Integer personalId) {
        reactionProcess(postId, personalId, false);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(readOnly = true)
    public List<Reaction> findAllByPostIdAndLikeTrue(Integer postId) {
        return reactionRepository.findAllByPostIdAndLikeTrue(postId);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(readOnly = true)
    public List<Reaction> findAllByPostIdAndLikeFalse(Integer postId) {
        return reactionRepository.findAllByPostIdAndLikeFalse(postId);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PERSONAL')")
    @Transactional(readOnly = true)
    public Map<String, List<KeyValueData<Date, Long>>> generateChartByPostReaction() {
        List<Integer> postIds = generateAllPostIdListByPersonal();
        if (CollectionUtils.isEmpty(postIds)) {
            return Collections.emptyMap();
        }
        Map<String, List<KeyValueData<Date, Long>>> chartDataMap = new HashMap<>();
        chartDataMap.put(PostUtil.POST_ALL, reactionRepository.generateAllPostWithoutReaction(postIds));
        chartDataMap.put(PostUtil.LIKE_ALL, reactionRepository.generateAllPostByReaction(postIds, true));
        chartDataMap.put(PostUtil.DISLIKE_ALL, reactionRepository.generateAllPostByReaction(postIds, false));
        return chartDataMap;
    }

    private void reactionProcess(Integer postId, Integer personalId, boolean status) {
        Reaction reaction = reactionRepository.findByPostIdAndPersonalId(postId, personalId);
        if (reaction == null) {
            reaction = new Reaction();
            reaction.setPostId(postId);
            reaction.setPersonalId(personalId);
        }
        reaction.setLike(status);
        reactionRepository.save(reaction);
    }

    private List<Integer> generateAllPostIdListByPersonal() {
        Personal personal = personalRepository.findByEmail(SecurityUtil.getUsername());
        List<Post> posts = postRepository.findAllByPersonal(personal);
        if (CollectionUtils.isEmpty(posts)) {
            return Collections.emptyList();
        }
        return posts.stream().map(Post::getId).collect(Collectors.toList());
    }
}
