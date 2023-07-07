package com.ems.controller;

import com.ems.Constants.MappingKeyConstants;
import com.ems.Constants.UrlConstants;
import com.ems.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = UrlConstants.TEST_URL)
    public String welcome() {
        return "test api";
    }

    @PostMapping(value = UrlConstants.LOGIN)
    public Map<String,String> generateToken(@RequestBody Map<String,String> authRequest) {
        Map<String,String> responce=new HashMap<>();
        System.out.println(authRequest.toString());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.get(MappingKeyConstants.USER_NAME),authRequest.get(MappingKeyConstants.PASSWORD))
            );
            responce.put(MappingKeyConstants.STATUS, HttpStatus.FOUND.toString());
            responce.put(MappingKeyConstants.TOKEN,jwtUtil.generateToken(authRequest.get(MappingKeyConstants.USER_NAME)));
        } catch (Exception ex) {
            responce.put(MappingKeyConstants.STATUS, HttpStatus.UNAUTHORIZED.toString());
        }
        return responce;
    }
}
