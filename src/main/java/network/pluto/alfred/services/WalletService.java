package network.pluto.alfred.services;

import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Wallet;

public interface WalletService {
    public Wallet createWallet(Member member);
}
