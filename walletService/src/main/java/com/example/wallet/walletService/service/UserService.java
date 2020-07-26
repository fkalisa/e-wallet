package com.example.wallet.walletService.service;

import com.example.wallet.walletService.dao.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "http://127.0.0.1:8080/api/users/";

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public List<User> findAll(){
        ResponseEntity<UserResponse> forEntity =
                restTemplate.getForEntity(BASE_URL, UserResponse.class);
        logger.info(forEntity.getHeaders().toString());
        if(forEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)){
            return null;
        }
        return forEntity.getBody().getList();
    }

    public User getUserById(String userId){
        final String uri = BASE_URL+ "{userId}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);

        ResponseEntity<User> forEntity =
                restTemplate.getForEntity(uri,  User.class,params);
        if(forEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)){
            return null;
        }
        return forEntity.getBody();
    }


}
