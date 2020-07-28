package com.example.wallet.walletService.service;

import com.example.wallet.walletService.dao.RedisWallet;
import com.example.wallet.walletService.dao.Wallet;
import com.example.wallet.walletService.exception.UserNotFoundException;
import com.example.wallet.walletService.exception.WalletNotFoundException;
import com.example.wallet.walletService.repository.WalletRepository;
import com.example.wallet.walletService.repository.WalletRepositoryRedis;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletRepositoryRedis walletRepositoryRedis;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Override
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Override
    public  Wallet getWalletByUserId(Integer id){
        return walletRepository.findWalletByUser_id(id);
    }

    @Override
    public Optional<Wallet> getWalletById(Integer id) throws WalletNotFoundException {

        RedisWallet redisWallet = walletRepositoryRedis.getWallet(id);
        Optional<Wallet> optWallet ;

        if(redisWallet == null){
            logger.info(String.format("...getting wallet %d from DB", id));
            optWallet = walletRepository.findById(id);
           if(optWallet.isPresent()){

               logger.info(String.format("...saving wallet %d to DB", id));
               walletRepositoryRedis.saveWallet(new ObjectMapper().convertValue(optWallet.get(), RedisWallet.class));
           }else{
               throw new WalletNotFoundException(String.format("Could not retrieve wallet with id %d", id));
           }
        }else{
            logger.info(String.format("...getting wallet %d from Redis", id));
            optWallet = Optional.ofNullable(new ObjectMapper().convertValue(redisWallet, Wallet.class));
        }

        return optWallet;
    }

    @Override
    public void createWallet(Wallet wallet) throws Exception {
        userService.getUserById(wallet.getUser_id()).ifPresentOrElse(value->{
            walletRepository.save(wallet);
        }, () ->  new UserNotFoundException(wallet.getUser_id()));
    }

    @Override
    public void updateWallet(Wallet wallet) throws Exception{
       if( !walletRepository.findById(wallet.getId()).isPresent()){
           throw new WalletNotFoundException(wallet.getId());
       }
        Wallet dbWallet = walletRepository.findById(wallet.getId()).get();




    }
}
