package com.example.wallet.walletService.repository;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.wallet.walletService.dao.RedisWallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class WalletRepositoryRedis {

    private static final Logger logger = LoggerFactory.getLogger(WalletRepositoryRedis.class);

    private static final String KEY = "wallet";

    @Autowired
    private RedisTemplate redisTemplate;


    public RedisWallet getWallet(Integer id) {
        Map walletMap = (Map) redisTemplate.opsForHash().get(KEY, Integer.toString(id));
        RedisWallet redisWallet = new ObjectMapper().convertValue(walletMap, RedisWallet.class);
        return redisWallet;
    }

    public Boolean saveWallet(RedisWallet redisWallet) {
        try {
            Map userHash = new ObjectMapper().convertValue(redisWallet, Map.class);
            redisTemplate.opsForHash().put(KEY, String.valueOf(redisWallet.getId()), userHash);
            return true;

        } catch (Exception e) {
            logger.error(String.format("Error while saving in cache the wallet with id %d ", redisWallet.getId()));
            return false;
        }
    }
}
