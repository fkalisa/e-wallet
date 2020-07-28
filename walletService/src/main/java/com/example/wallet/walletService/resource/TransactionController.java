package com.example.wallet.walletService.resource;

import com.example.wallet.walletService.dao.Transaction;
import com.example.wallet.walletService.exception.TransactionNotFoundException;
import com.example.wallet.walletService.exception.WalletNotFoundException;
import com.example.wallet.walletService.request.TransactionRequest;
import com.example.wallet.walletService.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    private final String BASE_PATH = "/api";

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @ApiOperation(value = "add a Balance")
    @PostMapping(BASE_PATH +"/addBalance")
    ResponseEntity<Transaction> addBalance(@RequestBody @Valid TransactionRequest transactionRequest, Errors errors) throws Exception {
        if (errors.hasErrors() ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Transaction transaction = new Transaction(transactionRequest.getAmount(),transactionRequest.getSid(),  transactionRequest.getRid(),  transactionRequest.getStatus() );
        ResponseEntity<Transaction> responseEntity;
        try {
            transactionService.addBalance(transaction);
            responseEntity = new ResponseEntity(transaction, HttpStatus.OK);
        }catch (WalletNotFoundException e){
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        } catch (Exception e){
            responseEntity = new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Get a Balance")
    @GetMapping(BASE_PATH+"/getBalance/{userId}")
    int getBalanceByUserId(@PathVariable int userId) throws Exception {
       int amount = -1;
        transactionService
                .getBalanceByUserId(userId).ifPresentOrElse(wallet -> {
           //TODO
                    wallet.getBalance();
        }, () ->new WalletNotFoundException(String.format("Wallet for userId %d not found", userId)));
        return amount;
    }

    @ApiOperation(value = "Retrieve a transaction for a given Id")
    @GetMapping(BASE_PATH+"/transactions/{id}")
    Transaction getTransactionByid(@PathVariable int id) throws Exception {
        return transactionService.getTransactionById(id).orElseThrow(()-> new TransactionNotFoundException(id));
    }


    @GetMapping(BASE_PATH+"/transactions/{userId}"+"/history")
    @ApiOperation(value = " To create and mail a .csv file of the transaction History ")
    @ResponseStatus(HttpStatus.OK)
    String getTransactionHistory(@PathVariable int userId) {
         transactionService.getTransactionHistoryByUserId(userId);
         return "file sent";
    }

}
