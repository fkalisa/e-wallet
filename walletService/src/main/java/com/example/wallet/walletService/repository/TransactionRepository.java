package com.example.wallet.walletService.repository;

import com.example.wallet.walletService.dao.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository  extends JpaRepository<Transaction, Integer> {
}
