package com.example.wallet.walletService.service;

import com.example.wallet.walletService.dao.Transaction;
import com.example.wallet.walletService.dao.User;
import com.example.wallet.walletService.dao.Wallet;
import com.example.wallet.walletService.exception.TransactionBadRequest;
import com.example.wallet.walletService.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private static final String TOPIC = "eWallet-";

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);


    @Override
    public void getTransactionHistoryByUserId(Integer userId) {
        String new_id = userId+"txn";
        kafkaTemplate.send(TOPIC, new_id);
    }

    @Override
    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    @Transactional
    @Override
    public void addBalance(Transaction transaction) throws Exception {

        Optional<User> optionalSender = userService.getUserById( transaction.getSid());
        Optional<User> optionalReceiver = userService.getUserById( transaction.getRid());

        if (optionalSender.isEmpty() || optionalReceiver.isEmpty() ){
            throw new TransactionBadRequest("No sender or no receiver found");
        }

        Wallet senderWallet = walletService.getWalletByUserId(optionalSender.get().getId());
        Wallet receiverWallet = walletService.getWalletByUserId(optionalReceiver.get().getId());

        if (senderWallet == null || receiverWallet == null){
            throw new TransactionBadRequest(String.format("No wallet found for sender or receiver"));
        }

        int amount = transaction.getAmount();
        if(senderWallet.getBalance() - amount < 0 ){
            throw new Exception("Not Sufficient Balance");

        }

        senderWallet.setBalance(senderWallet.getBalance()-amount);
        receiverWallet.setBalance(receiverWallet.getBalance()+amount);

        transactionRepository.save(transaction);
        walletService.updateWallet(senderWallet);
        walletService.updateWallet(receiverWallet);
    }

    @Override
    public Optional<Wallet> getBalanceByUserId(Integer userId) {
        return Optional.ofNullable(walletService.getWalletByUserId(userId));
    }
}
