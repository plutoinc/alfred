package network.pluto.alfred.controllers;

import network.pluto.alfred.models.User;
import network.pluto.alfred.models.Wallet;
import network.pluto.alfred.services.UserService;
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
    private final UserService userService;
    private final WalletService walletService;

    @Autowired
    public WalletController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<Wallet> createWallet(
            @Validated({IdPwValidationGroup.class}) @RequestBody User user) {

        User currentUser = this.userService.getUserById(user.getId());
        if(currentUser.getWallet() != null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }

        Wallet wallet = this.walletService.createWallet(currentUser);
        if (wallet == null) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }
}
