package com.example.user.userService.service;

import com.example.user.userService.dao.User;
import com.example.user.userService.dao.RedisUser;
import com.example.user.userService.repository.UserRepository;
import com.example.user.userService.repository.UserRepositoryRedis;
import com.example.user.userService.utility.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService  {
    @Autowired
    private UserRepositoryRedis userRepositoryRedis;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Optional<User> findById(Integer id) {
        Optional<User> user = null;

        RedisUser redisUser = userRepositoryRedis.findById(id);
        if(redisUser == null){
           user = userRepository.findById(id);
            userRepository.findById(id).ifPresent(value -> {});
            if (user .isPresent()){
                logger.info(String.format("Getting user %d from DB", id));
                logger.info(String.format("saving user %d in Redis", id));
                userRepositoryRedis.saveUser(Mapper.from(user.get()));
            }
        }else{

            user = Optional.of(Mapper.from(redisUser));
            logger.info(String.format("Getting user %d from Redis", id));
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
       return userRepository.save(user);
    }

    @Override
    public User update(User user) {

        RedisUser redisUser = userRepositoryRedis.findById(user.getId());
        redisUser.setAge(user.getAge());
        redisUser.setName(user.getName());
        redisUser.setSurname(user.getSurname());
        logger.info(String.format("Updating user %d in Redis", user.getId()));
        userRepositoryRedis.saveUser(Mapper.from(user));
        logger.info(String.format("Updating user %d in DB", user.getId()));
        userRepository.save(user);
        return userRepository.save(user);
    }
}
