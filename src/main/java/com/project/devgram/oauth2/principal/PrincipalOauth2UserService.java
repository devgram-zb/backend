package com.project.devgram.oauth2.principal;


import com.project.devgram.entity.Users;
import com.project.devgram.repository.UserRepository;
import com.project.devgram.type.ROLE;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

//Oauth2 로그인시 서비스
@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    private final UserRepository userRepository;

    @Override //후처리 용 함수  user 정보가 담겨있는 userRequest
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("OauthPrincipal login");
        OAuth2User oAuthUser = super.loadUser(userRequest);

        log.info("oAuth2User : "+oAuthUser);

        return processOAuth2User(oAuthUser);

    }

    public OAuth2User processOAuth2User(OAuth2User oAuth2User) {

        UUID str = UUID.randomUUID();
        String sumStr = String.valueOf(str);
        String extraWord = sumStr.substring(0,6);

        String providerId =String.valueOf((Object) oAuth2User.getAttribute("id"));
        String email = oAuth2User.getAttribute("email");
        String username ="github"+providerId;
        String role="ROLE_USER";

        Optional<Users> userEntity = userRepository.findByUsername(username);
        Users users;
        if(userEntity.isEmpty()){
            users = Users.builder()
                    .username(username)
                    .password(extraWord)
                    .email(email)
                    .role(ROLE.valueOf(role))
                    .providerId(providerId)
                    .build();
            log.info("프로필 data:  "+ oAuth2User.getAttributes());
            userRepository.save(users);

        }else {
            log.info("프로필 업데이트");
            users = userEntity.get();
            userRepository.save(users);

        }

        return new PrincipalDetails(users,oAuth2User.getAttributes());
    }
}
