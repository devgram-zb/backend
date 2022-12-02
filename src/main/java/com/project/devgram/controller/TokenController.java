package com.project.devgram.controller;


import com.project.devgram.oauth2.token.Token;
import com.project.devgram.oauth2.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/api/token/expired")
    public String auth(){
        throw new RuntimeException();
    }

    @GetMapping("/api/token/refresh")
    public String refreshAuth(HttpServletRequest request , HttpServletResponse response){
        String token = request.getHeader("Refresh");
        if (token != null && tokenService.validateToken(token)) {
            String email = tokenService.getUid(token);
            Token newToken = tokenService.generateToken(email, "USER");

            response.addHeader("Authentication", newToken.getToken());
            response.addHeader("Refresh", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "refresh success";
        }

        throw new RuntimeException();
    }



}
