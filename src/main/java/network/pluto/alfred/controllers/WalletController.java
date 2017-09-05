package network.pluto.alfred.controllers;

import network.pluto.alfred.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet/{userId}")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public @ResponseBody String createWallet(
            @PathVariable Long userId,
            @RequestParam(value="password") String password) {
        return this.walletService.createWallet(userId, password);
    }
}
