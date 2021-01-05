package com.example.tutorialthymeleaf.web.data.request;

import lombok.*;

/**
 * @author Iehor Funtusov, created 16/12/2020 - 3:55 AM
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageAndSizeData {

    int page;
    int size;
}
