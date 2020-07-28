package com.example.wallet.walletService.exception;

public class WalletBadRequest extends RuntimeException{

    public WalletBadRequest(String message) {
        super(message);
    }
    public WalletBadRequest(Integer id) {
        super(String.format("WalletBadRequest with transaction id %s", id));
    }
}
