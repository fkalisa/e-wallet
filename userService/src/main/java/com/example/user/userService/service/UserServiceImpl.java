package com.example.user.userService.service;

import com.example.user.userService.dao.User;
import com.example.user.userService.dao.RedisUser;
import com.example.user.userService.exception.UserNotFoundException;
import com.example.user.userService.repository.UserRepository;
import com.example.user.userService.repository.UserRepositoryRedis;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public Optional<User> findById(Integer id) throws UserNotFoundException {
        Optional<User> optionalUser;
        RedisUser redisUser = userRepositoryRedis.findById(id);
        if(redisUser == null){
            logger.info(String.format("...getting user %d from DB", id));
            optionalUser = userRepository.findById(id);
            if (optionalUser .isPresent()){

                logger.info(String.format("...saving user %d in Redis", id));
                userRepositoryRedis.saveUser(new ObjectMapper().convertValue(optionalUser.get(), RedisUser.class));
            }else{
                throw new UserNotFoundException(String.format("could not find a user with id", id));
            }
        }else{

            optionalUser = Optional.ofNullable(new ObjectMapper().convertValue(redisUser, User.class));
            logger.info(String.format("...getting user %d from Redis", id));
        }
        return optionalUser;
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
    public User update(User user) throws UserNotFoundException {

        RedisUser redisUser = userRepositoryRedis.findById(user.getId());
        if(redisUser == null){
            redisUser = new RedisUser();
        }

        redisUser.setAge(user.getAge());
        redisUser.setName(user.getName());
        redisUser.setSurname(user.getSurname());
        redisUser.setMobile(user.getMobile());
        redisUser.setEmail(user.getEmail());
        logger.info(String.format("...updating user %d in Redis", user.getId()));
        userRepositoryRedis.saveUser(redisUser);

        user.setAge(user.getAge());
        user.setName(user.getName());
        user.setSurname(user.getSurname());

        logger.info(String.format("...updating user %d in DB", user.getId()));
        return userRepository.save(user);
    }
}
