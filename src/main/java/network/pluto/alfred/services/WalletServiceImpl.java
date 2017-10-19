package network.pluto.alfred.services;

import network.pluto.alfred.transactions.PlutoCreateWalletTxRequest;
import network.pluto.bibliotheca.enums.WalletStatus;
import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Transaction;
import network.pluto.bibliotheca.models.Wallet;
import network.pluto.bibliotheca.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    @Transactional
    public Transaction createWallet(Member member) {
        Wallet wallet = new Wallet();
        wallet.setMember(member);
        wallet.setWalletStatus(WalletStatus.REQUEST_SENT);
        wallet = this.walletRepository.save(wallet);

        PlutoCreateWalletTxRequest plutoCreateWalletTxRequest =
                new PlutoCreateWalletTxRequest(member.getMemberId(), wallet.getWalletId());

        Transaction transaction = this.transactionService.sendTransaction(member, plutoCreateWalletTxRequest);
        if (transaction == null) {
            return null;
        }

        wallet.setTransaction(transaction);
        this.walletRepository.save(wallet);

        return transaction;
    }

    @Override
    @Transactional
    public Wallet registerAddress(long memberId, String address) {
        Wallet wallet = this.walletRepository.findByMember_MemberId(memberId);
        if (wallet == null) {
            return null;
        }

        wallet.setAddress(address);
        wallet.setWalletStatus(WalletStatus.CREATED);
        wallet = this.walletRepository.save(wallet);

        return wallet;
    }
}
