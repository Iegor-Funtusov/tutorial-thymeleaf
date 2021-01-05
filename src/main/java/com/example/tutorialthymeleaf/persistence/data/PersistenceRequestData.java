package com.example.tutorialthymeleaf.persistence.data;

import com.example.tutorialthymeleaf.util.WebRequestUtil;
import com.example.tutorialthymeleaf.web.data.request.PageAndSizeData;
import com.example.tutorialthymeleaf.web.data.request.SortData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @author Iehor Funtusov, created 04/01/2021 - 4:04 PM
 */

@Getter
@Setter
@NoArgsConstructor
public class PersistenceRequestData {

    private int page;
    private int size;
    private String sort;
    private String order;
    private boolean owner;
    private Map<String, Object> parameters;

    public PersistenceRequestData(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        this.page = pageAndSizeData.getPage();
        this.size = pageAndSizeData.getSize();
        this.sort = sortData.getSort();
        this.order = sortData.getOrder();
        this.owner = WebRequestUtil.getOwner(request);
    }
}
