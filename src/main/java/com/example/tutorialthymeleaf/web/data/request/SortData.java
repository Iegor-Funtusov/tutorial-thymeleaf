package com.example.tutorialthymeleaf.web.data.request;

import lombok.*;

/**
 * @author Iehor Funtusov, created 16/12/2020 - 3:56 AM
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SortData {

    private String sort;
    private String order;
}
