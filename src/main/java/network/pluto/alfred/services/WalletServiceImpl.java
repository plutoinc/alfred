package network.pluto.alfred.services;

import network.pluto.alfred.components.BlockchainClient;
import network.pluto.alfred.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private final BlockchainClient blockchainClient;

    @Autowired
    public WalletServiceImpl(BlockchainClient blockchainClient) {
        this.blockchainClient = blockchainClient;
    }

    @Override
    public String createWallet(User user) {
        return this.blockchainClient.createWallet(user);
    }
}
