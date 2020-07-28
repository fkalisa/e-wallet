package com.example.wallet.walletService.exception;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String message) {
        super(message);
    }

    public TransactionNotFoundException(Integer id) {
        super(String.format("Transaction with id %s not found : " , id));
    }
}
