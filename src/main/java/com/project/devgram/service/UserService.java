package com.project.devgram.service;

import com.project.devgram.dto.UserDto;

import com.project.devgram.entity.Users;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.UserErrorCode;
import com.project.devgram.repository.UserRepository;
import com.project.devgram.util.passUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserDto getUserDetails(String username){

        Users user = userRepository.findByUsername(username)
                .orElseThrow(()-> new DevGramException(UserErrorCode.USER_NOT_EXIST,
                        "해당하는 유저가 없습니다."));

            return UserDto.builder()
                    .userSeq(user.getUserSeq())
                    .job(user.getJob())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .annual(user.getAnnual())
                    .role(user.getRole())
                    .followCount(user.getFollowCount())
                    .followerCount(user.getFollowerCount())
                    .password(user.getPassword())
                    .providerId(user.getProviderId())
                    .build();
        }


    public void updateUserDetails(UserDto dto) {
        log.info("dtos {}",dto);

        Users user =userRepository.findByUsername(dto.getUsername())
                .orElseThrow(()-> new DevGramException(UserErrorCode.USER_NOT_EXIST));

        String encPassword = passUtil.encPassword(dto.getPassword());

            user.setPassword(encPassword);
            user.setJob(dto.getJob());
            user.setAnnual(dto.getAnnual());
            user.setUserSeq(dto.getUserSeq());

            userRepository.save(user);
    }

}
