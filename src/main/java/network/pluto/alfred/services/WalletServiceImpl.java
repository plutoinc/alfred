package network.pluto.alfred.services;

import network.pluto.alfred.transactions.CreateWalletTransactionData;
import network.pluto.alfred.transactions.TxName;
import network.pluto.alfred.transactions.TxRequest;
import network.pluto.bibliotheca.enums.WalletStatus;
import network.pluto.bibliotheca.models.Member;
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

    @Transactional
    @Override
    public void createWallet(Member member) {
        Wallet wallet = new Wallet();
        wallet.setMember(member);
        wallet.setWalletStatus(WalletStatus.REQUEST_SENT);
        wallet = this.walletRepository.save(wallet);

        CreateWalletTransactionData data = new CreateWalletTransactionData();
        data.setWalletId(wallet.getWalletId());

        TxRequest<CreateWalletTransactionData> request = TxRequest.create(member.getMemberId(), TxName.CREATE_WALLET, data);
        this.transactionService.sendTransaction(member, request);
    }

    @Transactional
    @Override
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
