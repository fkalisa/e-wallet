package com.example.wallet.walletService.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(Integer id) {
        super(String.format("User with id %s not found : " , id));
    }
}
