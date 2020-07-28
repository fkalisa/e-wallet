package com.example.user.userService.exception;

public class UserBadRequest extends RuntimeException {

    public UserBadRequest(String message) {
        super(message);
    }

    public UserBadRequest(Integer id) {
        super(String.format("Bad request for user %d", id));
    }
}
