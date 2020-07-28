package com.example.wallet.walletService.repository;

import com.example.wallet.walletService.dao.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository  extends JpaRepository<Wallet, Integer> {
    @Query("SELECT w FROM Wallet w WHERE w.user_id = ?1")
    Wallet findWalletByUser_id(Integer id);
}
