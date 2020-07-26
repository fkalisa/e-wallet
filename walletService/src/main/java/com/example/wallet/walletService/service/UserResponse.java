package com.example.wallet.walletService.service;

import com.example.wallet.walletService.dao.User;

import java.util.List;

public class UserResponse {
    private List<User> list;

    public List<User> getList() {
       return this.list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
