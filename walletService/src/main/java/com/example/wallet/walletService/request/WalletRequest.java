package com.example.wallet.walletService.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

public class WalletRequest {

    private int id;

    @NotEmpty
    private int user_id;

    @PositiveOrZero(message = "balance should be positive")
    @NotEmpty
    private int balance;

    @NotEmpty(message = "is_active should not be empty")
    private Boolean is_active;

    @NotEmpty(message = "wallet_type should not be empty")
    private String wallet_type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public String getWallet_type() {
        return wallet_type;
    }

    public void setWallet_type(String wallet_type) {
        this.wallet_type = wallet_type;
    }

    @Override
    public String toString() {
        return "WalletRequest{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", balance=" + balance +
                ", is_active=" + is_active +
                ", wallet_type='" + wallet_type + '\'' +
                '}';
    }
}
