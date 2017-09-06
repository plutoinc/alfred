package network.pluto.alfred.controllers;

import network.pluto.alfred.models.User;
import network.pluto.alfred.services.WalletService;
import network.pluto.alfred.validates.IdPwValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<User> createWallet(
            @Validated({IdPwValidationGroup.class}) @RequestBody User user) {
        String walletAddress = this.walletService.createWallet(user);
        if (walletAddress == null) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setWalletAddress(walletAddress);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
