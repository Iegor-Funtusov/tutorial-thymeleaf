package com.example.tutorialthymeleaf.util;

import com.example.tutorialthymeleaf.persistence.type.RoleType;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Iehor Funtusov, created 24/12/2020 - 1:56 PM
 */

public class SecurityUtil {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUsername() {
        Authentication authentication = SecurityUtil.getAuthentication();
        User principal = (User) authentication.getPrincipal();
        return principal.getUsername();
    }

    public static RoleType getRoleType() {
        Authentication authentication = SecurityUtil.getAuthentication();
        User principal = (User) authentication.getPrincipal();
        Collection<GrantedAuthority> authorities = principal.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();
        return RoleType.valueOf(authority.getAuthority());
    }

    public static boolean hasRole(String role) {
        Authentication authentication = SecurityUtil.getAuthentication();
        AtomicBoolean result = new AtomicBoolean(false);
        authentication.getAuthorities().forEach(authority -> result.set(authority.getAuthority().equalsIgnoreCase(role)));
        return result.get();
    }
}
