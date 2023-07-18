package com.toolbox.toolboxserver.domain.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.toolbox.toolboxserver.configuration.JwtTokenProvider;
import com.toolbox.toolboxserver.domain.auth.dto.GoogleUserInfo;
import com.toolbox.toolboxserver.domain.auth.dto.SignInResponse;
import com.toolbox.toolboxserver.domain.auth.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class LoginService {

    private Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    private final RestTemplate restTemplate = new RestTemplate();

    private JwtTokenProvider tokenProvider;

    private UserService userService;

    public LoginService(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.tokenProvider = jwtTokenProvider;
    }

    public String getAccessToken (String code) {

        String clientId = "676639504875-a0lpq23f7nvdnj3n4tojeh7mqc339jfg.apps.googleusercontent.com";
        String clientSecret = "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";
        String redirectUri = "http://localhost:8080/auth/google-login";
        String tokenUri = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

     public GoogleUserInfo getUserResource(String accessToken) {
        String resourceUri = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, GoogleUserInfo.class).getBody();
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
