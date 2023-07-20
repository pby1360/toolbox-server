package com.toolbox.toolboxserver.domain.auth.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.toolbox.toolboxserver.domain.auth.dto.SignInResponse;
import com.toolbox.toolboxserver.domain.auth.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger log = LoggerFactory.getLogger(getClass());

    private LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/google-login")
    public ResponseEntity loginWithGoogle (@RequestParam String code) throws URISyntaxException {
        log.info(":: loginWithGoogle");

        try {
            SignInResponse response = loginService.login(code);
            return ResponseEntity.ok(response);

        } catch (IllegalAccessException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}