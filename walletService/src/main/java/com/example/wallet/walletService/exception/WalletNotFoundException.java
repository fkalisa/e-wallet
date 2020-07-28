package com.example.wallet.walletService.exception;

public class WalletNotFoundException  extends RuntimeException {
    public WalletNotFoundException(Integer id) {
        super("Wallet id not found : " + id);
    }

    public WalletNotFoundException( String message) {
        super(message);
    }
}
