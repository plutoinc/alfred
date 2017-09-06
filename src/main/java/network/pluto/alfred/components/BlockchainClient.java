package network.pluto.alfred.components;

import network.pluto.alfred.models.User;

public interface BlockchainClient {
    public String getClientVersion();
    public String createWallet(User user);
}
