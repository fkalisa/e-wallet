package com.example.user.userService.repository;

import com.example.user.userService.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {
}
