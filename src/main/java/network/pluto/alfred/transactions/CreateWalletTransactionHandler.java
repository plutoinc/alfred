package network.pluto.alfred.transactions;

import network.pluto.alfred.components.BlockchainClientWeb3jImpl;
import network.pluto.alfred.contracts.Pluto;
import network.pluto.alfred.services.TransactionService;
import network.pluto.alfred.services.WalletService;
import network.pluto.bibliotheca.enums.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.util.List;

@Component
public class CreateWalletTransactionHandler implements TransactionHandler {

    private final TransactionService transactionService;
    private final BlockchainClientWeb3jImpl blockchainClient;
    private final WalletService walletService;

    @Autowired
    public CreateWalletTransactionHandler(WalletService walletService,
                                          TransactionService transactionService,
                                          BlockchainClientWeb3jImpl blockchainClientWeb3j) {
        this.walletService = walletService;
        this.transactionService = transactionService;
        this.blockchainClient = blockchainClientWeb3j;
    }

    @Transactional
    @Override
    public void handleTransaction(TxRequest<?> txRequest) throws Exception {
        TxRequest<CreateWalletTransactionData> convert = txRequest.convert(CreateWalletTransactionData.class);

        Pluto pluto = Pluto.load(
                blockchainClient.getPlutoContractAddr(),
                blockchainClient.getWeb3j(),
                blockchainClient.getCredentials(),
                ManagedTransaction.GAS_PRICE,
                Contract.GAS_LIMIT
        );

        TransactionReceipt transactionReceipt = pluto.createWallet(new Uint256(convert.getMemberId())).get();

        List<Pluto.WalletCreatedEventResponse> events = pluto.getWalletCreatedEvents(transactionReceipt);
        if (events.size() != 0) {
            Pluto.WalletCreatedEventResponse response = events.get(0);
            this.walletService.registerAddress(response.memberId.getValue().longValue(), response.walletAddr.toString());
            this.transactionService.updateTransactionStatus(txRequest.getTransaction().getId(), TransactionStatus.TX_SUCCESS);
        }
    }

    @Override
    public boolean supports(TxRequest<?> txRequest) {
        return txRequest.getTxName() == TxName.CREATE_WALLET;
    }
}
