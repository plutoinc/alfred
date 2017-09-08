package network.pluto.alfred.components;

import network.pluto.bibliotheca.models.Member;

public interface BlockchainClient {
    public String getClientVersion();
    public String createWallet(Member member);
}
