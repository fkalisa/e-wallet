package com.example.wallet.walletService.request;

import java.time.LocalDate;

public class TransactionRequest {
    private int id;
    private int amount;
    private int sid;//sender's id
    private int rid;//receiver id
    private LocalDate date;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "id=" + id +
                ", amount=" + amount +
                ", sid=" + sid +
                ", rid=" + rid +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
