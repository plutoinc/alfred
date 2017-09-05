package network.pluto.alfred.components;

public interface BlockchainClient {
    public String getClientVersion();
    public byte[] createWallet(String password);
}
