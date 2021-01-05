package com.example.tutorialthymeleaf.web.controller;

import com.example.tutorialthymeleaf.config.security.SecurityService;
import com.example.tutorialthymeleaf.facade.AuthValidatorFacade;
import com.example.tutorialthymeleaf.facade.RegistrationFacade;
import com.example.tutorialthymeleaf.persistence.type.RoleType;
import com.example.tutorialthymeleaf.util.SecurityUtil;
import com.example.tutorialthymeleaf.web.data.request.AuthData;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Iehor Funtusov, created 31/12/2020 - 9:55 AM
 */

@Controller
public class AuthController extends AbstractController {

    private final RegistrationFacade registrationFacade;
    private final AuthValidatorFacade authValidatorFacade;
    private final SecurityService securityService;

    public AuthController(RegistrationFacade registrationFacade, AuthValidatorFacade authValidatorFacade, SecurityService securityService) {
        this.registrationFacade = registrationFacade;
        this.authValidatorFacade = authValidatorFacade;
        this.securityService = securityService;
    }

    @GetMapping("/")
    public String main(Model model) {
        return redirectProcess(model);
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        showMessage(model, false);
        boolean authenticated = securityService.isAuthenticated();
        if (authenticated) {
            if (SecurityUtil.hasRole(RoleType.ROLE_ADMIN.name())) {
                return "redirect:/admin/dashboard";
            }
            if (SecurityUtil.hasRole(RoleType.ROLE_PERSONAL.name())) {
                return "redirect:/personal/dashboard";
            }
        }
        if (error != null) {
            showError(model, "Your email and password is invalid.");
        }
        if (logout != null) {
            showInfo(model, "You have been logged out successfully.");
        }
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return redirectProcess(model);
        }
        model.addAttribute("authForm", new AuthData());
        return "page/personal/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("authForm") AuthData authForm, BindingResult bindingResult, Model model) {
        showMessage(model, false);
        authValidatorFacade.validate(authForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "page/personal/registration";
        }
        registrationFacade.registration(authForm);
        securityService.autoLogin(authForm.getEmail(), authForm.getPasswordConfirm());
        return redirectProcess(model);
    }

    private String redirectProcess(Model model) {
        showMessage(model, false);
        if (SecurityUtil.hasRole(RoleType.ROLE_ADMIN.name())) {
            return "redirect:/admin/dashboard";
        }
        if (SecurityUtil.hasRole(RoleType.ROLE_PERSONAL.name())) {
            return "redirect:/personal/dashboard";
        }
        return "redirect:/login";
    }
}
