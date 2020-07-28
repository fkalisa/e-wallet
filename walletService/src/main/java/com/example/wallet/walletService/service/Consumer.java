package com.example.wallet.walletService.service;

import com.example.wallet.walletService.dao.Transaction;
import com.example.wallet.walletService.dao.User;
import com.example.wallet.walletService.exception.UserNotFoundException;
import com.example.wallet.walletService.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class Consumer {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = "eWallet-", groupId = "group_id")
    public void consume(String id) throws Exception {

        logger.info(String.format("...consumed Message -> %s",id));

        // Checking if the message is for txn History
        if(id.contains("txn")){
            sendTransactionHistory(id);
            return;
        }
        int t_id = Integer.parseInt(id);
        Optional<Transaction> transaction = transactionRepository.findById(t_id);
        Transaction transaction1 = transaction.get();
        int amt = transaction1.getAmount();
        Optional<User> optionalSender = userService.getUserById(transaction1.getSid());
        Optional<User> optionalReceiver = userService.getUserById(transaction1.getRid());
       if (optionalSender.isEmpty()){
           throw new UserNotFoundException(transaction1.getSid());
       }
        if (optionalReceiver.isEmpty()){
            throw new UserNotFoundException(transaction1.getRid());
        }

        EmailService.sendEmail(optionalSender.get().getEmail());
        EmailService.sendEmail(optionalReceiver.get().getEmail());
    }
    private void sendTransactionHistory(String id) throws Exception {
        String[] arrOfStr = id.split("t", 0);
        String new_id ="";
        for (String a : arrOfStr){
            new_id = a;
            break;
        }
        int id1 = Integer.parseInt(new_id);
        ArrayList<Transaction> list =
                (ArrayList<Transaction>) transactionRepository.findBysidAndrid(id1);

        Optional<User> optionalUser = userService.getUserById(id1);
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException(id1);
        }
        User user = optionalUser.get();
        String filename ="test.csv";
        try {
            FileWriter fw = new FileWriter(filename);

            for(int i=0;i<list.size();i++) {
                fw.append(list.get(i).getStatus());
                fw.append(',');
                int amt = list.get(i).getAmount();
                Integer obj = amt;
                fw.append(obj.toString());
                fw.append(',');
                fw.append(list.get(i).getDate().toString());
                fw.append(',');
                int id2 = list.get(i).getId();
                Integer obj2 = id2;
                fw.append(obj2.toString());
                fw.append(',');
                int rid = list.get(i).getRid();
                obj = rid;
                fw.append(obj.toString());
                fw.append(',');
                int sid = list.get(i).getSid();
                obj = sid;
                fw.append(obj.toString());
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            logger.info("CSV File is created successfully.");
            EmailService.sendEmailWithAttachments("","",user.getEmail(),"","toEmail@gmail.com","","",filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
