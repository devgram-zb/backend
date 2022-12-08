package com.project.devgram.oauth2.redis;

import com.project.devgram.type.ResponseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {

    private final TokenRedisRepository tokenRedisRepository;


    public void createRefresh(String id,String token,String type,Long period){
        log.info("createRefresh");
        log.info("username {} ",id);

        RedisUser user = RedisUser.builder()
                .id(id)
                .token(token)
                .type(type)
                .period(period)
                .build();


        tokenRedisRepository.save(user);
    }

    public void deleteRefresh(String id){
        log.info("delete RefreshToken");

        Optional<RedisUser> optionalToken = Optional.ofNullable(tokenRedisRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당하는 토큰이 없습니다.")));

        if(optionalToken.isPresent()){

            RedisUser user = optionalToken.get();

            tokenRedisRepository.delete(user);
            log.info("delete refreshToken");
        }else {
            log.error("refreshToken fail to find");
        }
    }


    public String getRefreshToken(String id) {


      Optional<RedisUser> optionalRedisUser = Optional.ofNullable(tokenRedisRepository.findById(id)
              .orElseThrow(() -> new NullPointerException("이미 만료되었거나 삭제된 토큰입니다.")));

      if(optionalRedisUser.isPresent()) {
          RedisUser user = optionalRedisUser.get();

          log.info("get success : {}",user.getId());
          return user.getId();

      }
      return String.valueOf(ResponseEnum.fail);
    }
}
