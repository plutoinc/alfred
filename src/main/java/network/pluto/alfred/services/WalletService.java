package network.pluto.alfred.services;

import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Transaction;
import network.pluto.bibliotheca.models.Wallet;

public interface WalletService {
    public Transaction createWallet(Member member);
    public Wallet registerAddress(long memberId, String address);
}
