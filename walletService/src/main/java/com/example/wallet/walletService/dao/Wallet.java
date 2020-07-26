package com.example.wallet.walletService.dao;

import javax.persistence.*;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "balance")
    private int balance;

    @Column(name = "is_active")
    private Boolean is_active;

    @Column(name = "wallet_type")
    private String wallet_type;

    public Wallet() {
    }

    public Wallet(int user_id, int balance, Boolean is_active, String wallet_type) {
        this.user_id = user_id;
        this.balance = balance;
        this.is_active = is_active;
        this.wallet_type = wallet_type;
    }

    public Wallet(int id, int user_id, int balance, Boolean is_active, String wallet_type) {
        this.id = id;
        this.user_id = user_id;
        this.balance = balance;
        this.is_active = is_active;
        this.wallet_type = wallet_type;
    }

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
        return "Wallet{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", balance=" + balance +
                ", is_active=" + is_active +
                ", wallet_type='" + wallet_type + '\'' +
                '}';
    }
}
