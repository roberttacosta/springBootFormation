package com.formacao.demo.controller;

import com.formacao.demo.dto.EmailDTO;
import com.formacao.demo.security.JWTUtil;
import com.formacao.demo.security.UserSpringSecurity;
import com.formacao.demo.service.AuthService;
import com.formacao.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    private JWTUtil jwtUtil;
    private UserService userService;
    private AuthService authService;

    public AuthController(JWTUtil jwtUtil, UserService userService, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/refresh_token")
    public void refreshToken(HttpServletResponse response) {
        UserSpringSecurity user = userService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/forgot")
    public void forgot(@Valid @RequestBody EmailDTO emailDTO) {
        authService.sendNewPassword(emailDTO.getEmail());
    }
}
