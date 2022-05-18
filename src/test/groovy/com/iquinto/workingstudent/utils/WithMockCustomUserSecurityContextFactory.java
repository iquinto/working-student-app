package com.iquinto.workingstudent.utils;

import com.iquinto.workingstudent.security.AuthUserDetails;
import com.iquinto.workingstudent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Autowired
    UserService userService;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        System.out.println("WithMockCustomUserSecurityContextFactory >>> " + customUser.username());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        AuthUserDetails principal = AuthUserDetails.build(userService.findByUsername(customUser.username()));

        Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        securityContext.setAuthentication(auth);
        return securityContext;
    }
}
