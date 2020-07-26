package com.example.wallet.walletService.resource;

import com.example.wallet.walletService.dao.Wallet;
import com.example.wallet.walletService.exception.WalletNotFoundException;
import com.example.wallet.walletService.request.WalletRequest;
import com.example.wallet.walletService.service.UserService;
import com.example.wallet.walletService.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WalletResource {

    @Autowired
    private WalletService walletService;

    @GetMapping("/wallets")
    @ResponseStatus(HttpStatus.OK)
    public List<Wallet> getWallets(){
        return walletService.findAll();
    }

    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid WalletRequest walletRequest, Errors errors){
        if (errors.hasErrors() ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Wallet wallet = new Wallet(walletRequest.getUser_id(), walletRequest.getBalance(), walletRequest.getIs_active(),walletRequest.getWallet_type() );
        walletService.createWallet(wallet);
        ResponseEntity<Wallet> responseEntity = new ResponseEntity(wallet, HttpStatus.OK);
        return responseEntity;
    }
    @PutMapping("/wallets/{id}")
    public void updateWalletById(@PathVariable Integer id, @RequestBody @Valid WalletRequest walletRequest){
    //TODO
    }
    @GetMapping("/wallets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Wallet getWalletById(@PathVariable Integer id){
        return walletService.getWalletById(id).orElseThrow(()-> new WalletNotFoundException(id));
    }
}
