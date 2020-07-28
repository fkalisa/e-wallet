package com.example.wallet.walletService.exception;

public class TransactionBadRequest extends Exception {

    public TransactionBadRequest(Integer id){
        super(String.format("TransactionBadRequest with transaction id %s", id));
    }
    public TransactionBadRequest(String message){
        super(message);
    }
}
