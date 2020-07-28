package com.example.wallet.walletService.service;

import com.example.wallet.walletService.dao.Transaction;
import com.example.wallet.walletService.dao.Wallet;

import java.util.Optional;

public interface TransactionService {
    void getTransactionHistoryByUserId(Integer userId);

    Optional<Transaction> getTransactionById(Integer id);

    void addBalance(Transaction transaction) throws Exception;

    Optional<Wallet> getBalanceByUserId(Integer userId);
}
