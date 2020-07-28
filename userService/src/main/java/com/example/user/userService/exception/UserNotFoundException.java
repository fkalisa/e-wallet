package com.example.user.userService.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super("User not found " + id);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
