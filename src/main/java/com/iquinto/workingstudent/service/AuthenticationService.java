package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.security.AuthUserDetails;
import com.iquinto.workingstudent.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    public String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        log.info("AuthenticationService:[getCurrentUsername] " + currentPrincipalName);
        return currentPrincipalName;
    }

    public LinkedHashMap<String,Object>  setAuthentication(String username, String password ){
        LinkedHashMap<String,Object> hashMap = new LinkedHashMap();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.generateJwtToken(authentication);

            AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            hashMap.put("token", token);
            hashMap.put("roles", roles);
            return hashMap;
    }

}
