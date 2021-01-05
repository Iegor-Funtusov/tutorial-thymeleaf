package com.example.tutorialthymeleaf.facade.impl;

import com.example.tutorialthymeleaf.facade.PersonalAdminFacade;
import com.example.tutorialthymeleaf.persistence.data.PersistenceRequestData;
import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import com.example.tutorialthymeleaf.service.post.PersonalAdminService;
import com.example.tutorialthymeleaf.util.WebRequestUtil;
import com.example.tutorialthymeleaf.web.data.request.PageAndSizeData;
import com.example.tutorialthymeleaf.web.data.request.PersonalData;
import com.example.tutorialthymeleaf.web.data.request.SortData;
import com.example.tutorialthymeleaf.web.data.response.PageData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Iehor Funtusov, created 04/01/2021 - 5:45 AM
 */

@Service
public class PersonalAdminFacadeImpl implements PersonalAdminFacade {

    private final PersonalAdminService personalAdminService;

    public PersonalAdminFacadeImpl(PersonalAdminService personalAdminService) {
        this.personalAdminService = personalAdminService;
    }

    @Override
    public PageData<PersonalData> findAll(WebRequest request) {
        PersistenceRequestData data = new PersistenceRequestData(request);
        PageData<PersonalData> pageData = new PageData<>();
        pageData.setSort(data.getSort());
        pageData.setOrder(data.getOrder());
        Page<Personal> personals = personalAdminService.findAll(data);
        pageData.setCurrentPage(data.getPage());
        pageData.setPageSize(data.getSize());
        pageData.setTotalElements(personals.getTotalElements());
        pageData.setTotalPages(personals.getTotalPages());
        if (CollectionUtils.isNotEmpty(personals.getContent())) {
            List<PersonalData> list = personals.getContent().stream().map(PersonalData::new).collect(Collectors.toList());
            pageData.setItems(list);
        }
        pageData.initPaginationState(data.getPage());
        return pageData;
    }

    @Override
    public PersonalData findById(Integer id) {
        return new PersonalData(personalAdminService.findById(id));
    }

    @Override
    public void delete(Integer id) {
        personalAdminService.delete(id);
    }

    @Override
    public void lockAccount(Integer id) {
        personalAdminService.lockAccount(id);
    }

    @Override
    public void unlockAccount(Integer id) {
        personalAdminService.unlockAccount(id);
    }
}
