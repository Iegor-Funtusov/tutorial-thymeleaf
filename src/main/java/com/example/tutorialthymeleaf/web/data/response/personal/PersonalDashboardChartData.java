package com.example.tutorialthymeleaf.web.data.response.personal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Iehor Funtusov, created 02/01/2021 - 4:08 AM
 */

@Getter
@Setter
@ToString
public class PersonalDashboardChartData {

    private List<Date> labels;
    private List<Long> allPost;
    private List<Long> likePost;
    private List<Long> dislikePost;

    public PersonalDashboardChartData() {
        this.labels = Collections.emptyList();
        this.allPost = Collections.emptyList();
        this.likePost = Collections.emptyList();
        this.dislikePost = Collections.emptyList();
    }
}
