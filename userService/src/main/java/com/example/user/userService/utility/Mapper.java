package com.example.user.userService.utility;

import com.example.user.userService.dao.User;
import com.example.user.userService.dao.RedisUser;

public class Mapper {

    public static User from(RedisUser redisUser) {
        User user = new User(redisUser.getId(),redisUser.getName(), redisUser.getSurname(), redisUser.getAge());
        return user;
    }

    public static RedisUser from(User user) {
        RedisUser redisUser = new RedisUser();
        redisUser.setId(user.getId());

        redisUser.setAge(user.getAge());
        redisUser.setName(user.getName());
        redisUser.setSurname(user.getSurname());
        return redisUser;
    }
}
