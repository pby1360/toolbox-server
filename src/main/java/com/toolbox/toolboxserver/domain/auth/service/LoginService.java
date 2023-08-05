package com.toolbox.toolboxserver.domain.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.toolbox.toolboxserver.configuration.JwtTokenProvider;
import com.toolbox.toolboxserver.domain.auth.dto.GoogleUserInfo;
import com.toolbox.toolboxserver.domain.auth.dto.SignInResponse;
import com.toolbox.toolboxserver.domain.auth.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class LoginService {
    @Value("${login.google.client-id}")
    private String CLIENT_ID;
    @Value("${login.google.client-secret}")
    private String CLIENT_SECRET;
    @Value("${login.google.redirect-uri}")
    private String REDIRECT_URI;
    @Value("${login.google.token-uri}")
    private String TOKEN_URI;
    @Value("${login.google.resource-uri}")
    private String RESOURCE_URI;

    private Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    private final RestTemplate restTemplate = new RestTemplate();

    private JwtTokenProvider tokenProvider;

    private UserService userService;

    public LoginService(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.tokenProvider = jwtTokenProvider;
    }

    public String getAccessToken (String code) {

        log.info(":: REDIRECT_URI ? {}", REDIRECT_URI);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(TOKEN_URI, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();

    }

     public GoogleUserInfo getUserResource(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(RESOURCE_URI, HttpMethod.GET, entity, GoogleUserInfo.class).getBody();
    }

    public SignInResponse login(String code)  throws IllegalAccessException{

        String accessToken = getAccessToken(code);
        GoogleUserInfo userInfo = getUserResource(accessToken);
        log.info(userInfo.toString());

        User user = null;

        if (!userService.isExist(userInfo.getEmail())) {
            User newUser = User.createUser(userInfo.getEmail(), userInfo.getName(), "google");
            user = userService.join(newUser);
        } else {
            user = userService.getUserByEmail(userInfo.getEmail());
        }

        // TODO: 2023-03-22 need to add Permission
        String token = tokenProvider.createToken(user.getEmail(), Collections.emptyList());
        return SignInResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(null)
                .token(token)
                .expiredAt(tokenProvider.getExpiration(token).getTime())
                .build();
    }
}
