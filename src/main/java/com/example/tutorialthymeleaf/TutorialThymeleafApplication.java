package com.example.tutorialthymeleaf;

import com.example.tutorialthymeleaf.persistence.entity.user.Admin;
import com.example.tutorialthymeleaf.persistence.entity.user.Personal;
import com.example.tutorialthymeleaf.persistence.repository.user.AdminRepository;
import com.example.tutorialthymeleaf.persistence.repository.user.PersonalRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

@SpringBootApplication
public class TutorialThymeleafApplication {

    private final AdminRepository adminRepository;
    private final PersonalRepository personalRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${initPersonals}")
    private boolean initPersonals;

    public TutorialThymeleafApplication(AdminRepository adminRepository, PersonalRepository personalRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminRepository = adminRepository;
        this.personalRepository = personalRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(TutorialThymeleafApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws IOException {
        String email = "admin@gmail.com";
        String password = "12345678";
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) {
            admin = new Admin();
            admin.setEmail(email);
            admin.setPassword(bCryptPasswordEncoder.encode(password));
            adminRepository.save(admin);
        }
        if (initPersonals) {
            System.out.println("start init");
            for (int i = 0; i < 111; i++) {
                Personal personal = new Personal();
                personal.setPassword(bCryptPasswordEncoder.encode(password));
                personal.setEmail("personal" + i + "@gmail.com");
                personalRepository.save(personal);
            }
            System.out.println("finish init");
        }
    }
}
