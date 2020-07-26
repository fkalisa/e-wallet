package com.example.wallet.walletService.repository;

import com.example.wallet.walletService.dao.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

@Repository
public interface WalletRepository  extends JpaRepository<Wallet, Integer> {
}
