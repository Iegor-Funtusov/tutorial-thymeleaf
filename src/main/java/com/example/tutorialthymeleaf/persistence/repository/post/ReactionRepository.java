package com.example.tutorialthymeleaf.persistence.repository.post;

import com.example.tutorialthymeleaf.persistence.entity.post.Reaction;
import com.example.tutorialthymeleaf.persistence.repository.AbstractRepository;
import com.example.tutorialthymeleaf.web.data.KeyValueData;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 12:22 PM
 */

@Repository
public interface ReactionRepository extends AbstractRepository<Reaction> {

    List<Reaction> findAllByPostId(Integer id);
    List<Reaction> findAllByPostIdAndLikeTrue(Integer id);
    List<Reaction> findAllByPostIdAndLikeFalse(Integer id);
    Reaction findByPostIdAndPersonalId(Integer postId, Integer personalId);

    @Query("select new com.example.tutorialthymeleaf.web.data.KeyValueData(reaction.created, count(reaction.like)) " +
            "from Reaction as reaction where reaction.postId in :postIds " +
            "group by reaction.created order by reaction.created asc ")
    List<KeyValueData<Date, Long>> generateAllPostWithoutReaction(@Param("postIds") List<Integer> postIds);

    @Query("select new com.example.tutorialthymeleaf.web.data.KeyValueData(reaction.created, count(reaction.like)) " +
            "from Reaction as reaction where reaction.postId in :postIds and reaction.like = :react " +
            "group by reaction.created order by reaction.created asc ")
    List<KeyValueData<Date, Long>> generateAllPostByReaction(@Param("postIds") List<Integer> postIds, @Param("react") Boolean react);
}
