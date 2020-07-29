package com.example.wallet.walletService.resource;

import com.example.wallet.walletService.dao.Wallet;
import com.example.wallet.walletService.exception.WalletBadRequest;
import com.example.wallet.walletService.exception.WalletNotFoundException;
import com.example.wallet.walletService.request.WalletRequest;
import com.example.wallet.walletService.service.WalletService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    private final String BASE_PATH = "/api/wallets";


    @ApiOperation("Find all the wallets")
    @GetMapping(BASE_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<Wallet> findAll(){
        return walletService.findAll();
    }

    @ApiOperation("Create a wallet")
    @PostMapping(BASE_PATH)
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid WalletRequest walletRequest, Errors errors){
        if (errors.hasErrors() ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Wallet wallet = new Wallet(walletRequest.getUser_id(), walletRequest.getBalance(), walletRequest.getIs_active(),walletRequest.getWallet_type() );
        ResponseEntity<Wallet> responseEntity;
        try {
            walletService.createWallet(wallet);
            responseEntity = new ResponseEntity(wallet, HttpStatus.OK);
        }catch (HttpClientErrorException e){
            responseEntity = new ResponseEntity<>(e.getStatusCode());
            logger.error(String.format("could not retrieve userId %s while creating a wallet : %s", wallet.getUser_id(), e.getMessage()));

        } catch (Exception e) {
            logger.error(String.format("could not retrieve userId %s while creating a wallet : %s", wallet.getUser_id(), e.getMessage()));
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @ApiOperation("Update a wallet for a given Id")
    @PutMapping(BASE_PATH +"/{id}")
    public void updateWalletById(@PathVariable Integer id, @RequestBody @Valid WalletRequest walletRequest, Errors errors){

        if (errors.hasErrors()) {
            throw new WalletBadRequest(id);
        }
        walletService.getWalletById(id).ifPresentOrElse(wallet->{
            wallet.setBalance(walletRequest.getBalance());
            try {
                walletService.updateWallet(wallet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ()->new WalletNotFoundException(id));
    }
    @ApiOperation("Retrieve a wallet for a given Id")
    @GetMapping(BASE_PATH +"/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Wallet getWalletById(@PathVariable Integer id){
        return walletService.getWalletById(id).orElseThrow(()-> new WalletNotFoundException(id));
    }
}
