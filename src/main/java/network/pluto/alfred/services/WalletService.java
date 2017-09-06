package network.pluto.alfred.services;

import network.pluto.alfred.models.User;
import network.pluto.alfred.models.Wallet;

public interface WalletService {
    public Wallet createWallet(User user);
}
