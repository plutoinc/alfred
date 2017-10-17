package network.pluto.alfred.components;

import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Wallet;

public interface BlockchainClient {
    public String getClientVersion();
    public Boolean sendCreateWalletReq(Member member, Wallet wallet);
}
