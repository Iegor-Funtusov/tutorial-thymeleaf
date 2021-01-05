package com.example.tutorialthymeleaf.facade;

import com.example.tutorialthymeleaf.web.data.request.PostData;
import com.example.tutorialthymeleaf.web.data.response.PageData;
import com.example.tutorialthymeleaf.web.data.response.PostResponseData;
import com.example.tutorialthymeleaf.web.data.response.personal.PersonalDashboardChartData;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Iehor Funtusov, created 25/12/2020 - 12:33 PM
 */

public interface PostFacade {

    void create(PostData data);
    void update(PostData data, Integer id);
    void delete(Integer id);
    PageData<PostData> findAll(WebRequest request);
    PostResponseData findById(Integer id);
    void like(Integer id);
    void dislike(Integer id);
    void uploadFile(MultipartFile file, Integer postId);
    PersonalDashboardChartData generatePersonalDashboardChartData();
}
