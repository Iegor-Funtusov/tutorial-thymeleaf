package com.example.tutorialthymeleaf.service.post.impl;

import com.example.tutorialthymeleaf.persistence.data.PersistenceRequestData;
import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import com.example.tutorialthymeleaf.persistence.repository.user.PersonalRepository;
import com.example.tutorialthymeleaf.service.post.PersonalAdminService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.tutorialthymeleaf.util.WebRequestUtil.DEFAULT_ORDER_PARAM_VALUE;

/**
 * @author Iehor Funtusov, created 03/01/2021 - 8:31 AM
 */

@Service
public class PersonalAdminServiceImpl implements PersonalAdminService {

    private final PersonalRepository personalRepository;

    public PersonalAdminServiceImpl(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public Page<Personal> findAll(PersistenceRequestData data) {
        Sort sort = data.getOrder().equals(DEFAULT_ORDER_PARAM_VALUE) ? Sort.by(Sort.Order.desc(data.getSort())) : Sort.by(Sort.Order.asc(data.getSort()));
        return personalRepository.findAll(PageRequest.of(data.getPage() - 1, data.getSize(), sort));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public Personal findById(Integer id) {
        Personal personal = personalRepository.findById(id).orElse(null);
        if (personal == null) {
            throw new RuntimeException("personal not found by id " + id);
        }
        return personal;
    }

    @Override
    public void create(Personal personal) {

    }

    @Override
    public void update(Personal personal) {

    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void delete(Integer id) {
        Personal personal = personalRepository.findById(id).orElse(null);
        if (personal == null) {
            throw new RuntimeException("personal not found by id " + id);
        }
        personalRepository.delete(personal);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void lockAccount(Integer id) {
        enabledAccountProcess(id, false);
    }

    @Override
    public void unlockAccount(Integer id) {
        enabledAccountProcess(id, true);
    }

    private void enabledAccountProcess(Integer id, boolean enabled) {
        Personal personal = personalRepository.findById(id).orElse(null);
        if (personal == null) {
            throw new RuntimeException("personal not found by id " + id);
        }
        personal.setEnabled(enabled);
        personalRepository.save(personal);
    }
}
