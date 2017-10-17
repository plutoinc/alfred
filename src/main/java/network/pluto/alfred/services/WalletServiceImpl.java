package network.pluto.alfred.services;

import network.pluto.alfred.components.BlockchainClient;
import network.pluto.alfred.transactions.PlutoCreateWalletTxRequest;
import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Wallet;
import network.pluto.bibliotheca.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final TransactionService transactionService;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository, TransactionService transactionService) {
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
    }

    @Override
    public Boolean createWallet(Member member) {

        Wallet wallet = new Wallet();
        wallet = this.walletRepository.save(wallet);

        PlutoCreateWalletTxRequest plutoCreateWalletTxRequest =
                new PlutoCreateWalletTxRequest(member.getMemberId(), wallet.getWalletId());
        return this.transactionService.sendTxReq(plutoCreateWalletTxRequest);
    }
}
