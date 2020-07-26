package com.example.wallet.walletService.service;

import com.example.wallet.walletService.dao.Wallet;
import com.example.wallet.walletService.repository.WalletRepository;
import com.example.wallet.walletService.repository.WalletRepositoryRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepositry;

    @Autowired
    private WalletRepositoryRedis walletRepositoryRedis;

    @Autowired
    private UserService userService;

    @Override
    public List<Wallet> findAll() {
        return walletRepositry.findAll();
    }

    @Override
    public Optional<Wallet> getWalletById(Integer id) {
        return walletRepositry.findById(id);
    }

    @Override
    public void createWallet(Wallet wallet) {


        walletRepositry.save(wallet);
    }
}
