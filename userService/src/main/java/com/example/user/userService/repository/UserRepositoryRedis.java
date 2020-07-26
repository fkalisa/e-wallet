package com.example.user.userService.repository;

import com.example.user.userService.dao.RedisUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepositoryRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY = "user";

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryRedis.class);

    public Boolean saveUser(RedisUser user) {
        try {
            Map userHash = new ObjectMapper().convertValue(user, Map.class);
            redisTemplate.opsForHash().put(KEY, String.valueOf(user.getId()), userHash);
            return true;

        } catch (Exception e) {
            logger.error(String.format("Error while saving in cache the user with id %d ", user.getId()));
            return false;
        }
    }

    public RedisUser findById(Integer id) {
        Map<String, String> userMap = (Map) redisTemplate.opsForHash().get(KEY, String.valueOf(id));
        RedisUser user = new ObjectMapper().convertValue(userMap, RedisUser.class);
        return user;
    }

}
