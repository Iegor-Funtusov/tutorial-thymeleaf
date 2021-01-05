package com.example.tutorialthymeleaf.service.post;

import com.example.tutorialthymeleaf.persistence.entity.post.Reaction;
import com.example.tutorialthymeleaf.web.data.KeyValueData;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 12:30 PM
 */

public interface ReactionService {

    void deleteByPostId(Integer postId);
    void like(Integer rostId, Integer personalId);
    void dislike(Integer rostId, Integer personalId);
    List<Reaction> findAllByPostIdAndLikeTrue(Integer postId);
    List<Reaction> findAllByPostIdAndLikeFalse(Integer postId);
    Map<String, List<KeyValueData<Date, Long>>> generateChartByPostReaction();
}
