package com.example.wallet.walletService.service;

import com.example.wallet.walletService.dao.Wallet;
import com.example.wallet.walletService.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface WalletService {

    List<Wallet> findAll();

    Optional<Wallet> getWalletById(Integer id);

    void createWallet(Wallet wallet) throws Exception;

    void updateWallet(Wallet wallet) throws Exception;

    Wallet getWalletByUserId(Integer id);
}
