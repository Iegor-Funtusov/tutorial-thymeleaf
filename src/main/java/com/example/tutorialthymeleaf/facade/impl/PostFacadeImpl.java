package com.example.tutorialthymeleaf.facade.impl;

import com.example.tutorialthymeleaf.facade.PostFacade;
import com.example.tutorialthymeleaf.persistence.data.PersistenceRequestData;
import com.example.tutorialthymeleaf.persistence.entity.post.Post;
import com.example.tutorialthymeleaf.persistence.entity.post.Reaction;
import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import com.example.tutorialthymeleaf.service.post.PostService;
import com.example.tutorialthymeleaf.service.post.ReactionService;
import com.example.tutorialthymeleaf.service.user.PersonalService;
import com.example.tutorialthymeleaf.util.PostUtil;
import com.example.tutorialthymeleaf.web.data.request.PostData;
import com.example.tutorialthymeleaf.web.data.response.PageData;
import com.example.tutorialthymeleaf.web.data.response.PostResponseData;
import com.example.tutorialthymeleaf.web.data.KeyValueData;
import com.example.tutorialthymeleaf.web.data.response.personal.PersonalDashboardChartData;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 12:34 PM
 */

@Service
public class PostFacadeImpl implements PostFacade {

    private final PostService postService;
    private final ReactionService reactionService;
    private final PersonalService personalService;

    public PostFacadeImpl(PostService postService, ReactionService reactionService, PersonalService personalService) {
        this.postService = postService;
        this.reactionService = reactionService;
        this.personalService = personalService;
    }

    @Override
    public void create(PostData data) {
        Post post = new Post();
        post.setMessage(data.getMessage());
        post.setTitle(data.getTitle());
        post.setComment(data.getComment());
        postService.create(post);
    }

    @Override
    public void update(PostData data, Integer id) {
        Post post = postService.findById(id);
        post.setMessage(data.getMessage());
        post.setTitle(data.getTitle());
        post.setComment(data.getComment());
        postService.update(post);
    }

    @Override
    public void delete(Integer id) {
        postService.delete(id);
    }

    @Override
    public PageData<PostData> findAll(WebRequest request) {
        PersistenceRequestData persistenceRequestData = new PersistenceRequestData(request);
        Page<Post> page = postService.findAll(persistenceRequestData);
        PageData<PostData> data = new PageData<>();
        data.setCurrentPage(page.getNumber());
        data.setPageSize(page.getNumber());
        data.setTotalElements(page.getTotalPages());
        data.setTotalPages(page.getTotalPages());
        if (CollectionUtils.isNotEmpty(page.getContent())) {
            List<PostData> list = page.getContent().stream().map(PostData::new).collect(Collectors.toList());
            data.setItems(list);
        }
        return data;
    }

    @Override
    public PostResponseData findById(Integer id) {
        Post post = postService.findById(id);
        List<Reaction> likeReactionList = reactionService.findAllByPostIdAndLikeTrue(post.getId());
        List<Reaction> dislikeReactionList = reactionService.findAllByPostIdAndLikeFalse(post.getId());
        PostResponseData data = new PostResponseData(post);
        if (CollectionUtils.isNotEmpty(likeReactionList)) {
            List<Integer> ids = likeReactionList.stream().map(Reaction::getPersonalId).collect(Collectors.toList());
            generatePostResponseData(data, ids, null);
        }
        if (CollectionUtils.isNotEmpty(dislikeReactionList)) {
            List<Integer> ids = dislikeReactionList.stream().map(Reaction::getPersonalId).collect(Collectors.toList());
            generatePostResponseData(data, null, ids);
        }
        return data;
    }

    @Override
    public void like(Integer id) {
        postService.like(id);
    }

    @Override
    public void dislike(Integer id) {
        postService.dislike(id);
    }

    @Override
    public void uploadFile(MultipartFile file, Integer postId) {
        postService.uploadFile(file, postId);
    }

    @Override
    public PersonalDashboardChartData generatePersonalDashboardChartData() {
        PersonalDashboardChartData data = new PersonalDashboardChartData();
        Map<String, List<KeyValueData<Date, Long>>> chartDataMap = reactionService.generateChartByPostReaction();
        if (MapUtils.isEmpty(chartDataMap)) {
            return data;
        }

        List<KeyValueData<Date, Long>> allPostData = chartDataMap.get(PostUtil.POST_ALL);
        List<KeyValueData<Date, Long>> likePostData = chartDataMap.get(PostUtil.LIKE_ALL);
        List<KeyValueData<Date, Long>> dislikePostData = chartDataMap.get(PostUtil.DISLIKE_ALL);

        List<Long> allPost = allPostData.stream().map(KeyValueData::getValue).collect(Collectors.toUnmodifiableList());
        List<Long> likePost = new ArrayList<>();
        List<Long> dislikePost = new ArrayList<>();

        List<Date> dates = allPostData.stream().map(KeyValueData::getKey).collect(Collectors.toUnmodifiableList());

        KeyValueData<Date, Long> keyValue;
        for (Date reactionDate : dates) {
            keyValue = likePostData.stream().filter(keyValueData -> keyValueData.getKey().getTime() == reactionDate.getTime()).findAny().orElse(null);
            if (keyValue == null) {
                likePost.add(0L);
            } else {
                likePost.add(keyValue.getValue());
            }
            keyValue = dislikePostData.stream().filter(keyValueData -> keyValueData.getKey().getTime() == reactionDate.getTime()).findAny().orElse(null);
            if (keyValue == null) {
                dislikePost.add(0L);
            } else {
                dislikePost.add(keyValue.getValue());
            }
        }

        data.setLabels(dates);
        data.setAllPost(allPost);
        data.setLikePost(likePost);
        data.setDislikePost(dislikePost);

        return data;
    }

    private void generatePostResponseData(PostResponseData data, List<Integer> likeIds, List<Integer> dislikeIds) {
        List<Personal> personals;
        Map<Integer, String> map;
        if (likeIds != null) {
            personals = personalService.findAllByListId(likeIds);
            map = personals.stream().collect(Collectors.toMap(Personal::getId, Personal::getFullName));
            data.setLikes(map);
        }
        if (dislikeIds != null) {
            personals = personalService.findAllByListId(dislikeIds);
            map = personals.stream().collect(Collectors.toMap(Personal::getId, Personal::getFullName));
            data.setDislikes(map);
        }
    }
}
