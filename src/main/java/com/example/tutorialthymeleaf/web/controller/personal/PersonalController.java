package com.example.tutorialthymeleaf.web.controller.personal;

import com.example.tutorialthymeleaf.facade.PostFacade;
import com.example.tutorialthymeleaf.web.data.response.personal.PersonalDashboardChartData;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Iehor Funtusov, created 31/12/2020 - 10:17 AM
 */

@Controller
@RequestMapping("/personal")
public class PersonalController {

    private final PostFacade postFacade;

    public PersonalController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @GetMapping
    public String main() {
        return "page/personal/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "page/personal/dashboard";
    }

    @GetMapping("/dashboard/chart")
    public @ResponseBody ResponseEntity<PersonalDashboardChartData> generateChart() {
        return ResponseEntity.ok(postFacade.generatePersonalDashboardChartData());
    }
}
