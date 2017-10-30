package network.pluto.alfred.services;

import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Wallet;

public interface WalletService {
    void createWallet(Member member);
    Wallet registerAddress(long memberId, String address);
}
