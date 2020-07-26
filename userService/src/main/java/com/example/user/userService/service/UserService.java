package com.example.user.userService.service;

import com.example.user.userService.dao.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Integer id);

    List<User> findAll();

    User save(User user);

     User update(User user);
}
