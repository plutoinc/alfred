package network.pluto.alfred.services;

import network.pluto.alfred.components.BlockchainClient;
import network.pluto.alfred.models.User;
import network.pluto.alfred.models.Wallet;
import network.pluto.alfred.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final BlockchainClient blockchainClient;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository, BlockchainClient blockchainClient) {
        this.walletRepository = walletRepository;
        this.blockchainClient = blockchainClient;
    }

    @Override
    public Wallet createWallet(User user) {
        String walletAddress = this.blockchainClient.createWallet(user);
        if(walletAddress == null) {
            return null;
        }

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setAddress(walletAddress);

        wallet = this.walletRepository.save(wallet);

        return wallet;
    }
}
