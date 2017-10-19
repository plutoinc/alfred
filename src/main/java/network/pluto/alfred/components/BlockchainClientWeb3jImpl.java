package network.pluto.alfred.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import network.pluto.alfred.contracts.Pluto;
import network.pluto.alfred.services.TransactionService;
import network.pluto.alfred.services.WalletService;
import network.pluto.alfred.transactions.PlutoCreateWalletTxRequest;
import network.pluto.alfred.transactions.PlutoTxName;
import network.pluto.bibliotheca.enums.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import rx.Subscription;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class BlockchainClientWeb3jImpl implements BlockchainClient {
    private static final Logger logger = LoggerFactory.getLogger(BlockchainClientWeb3jImpl.class);

    private final TransactionService transactionService;
    private final WalletService walletService;
    private final Web3j web3j;
    private final Credentials credentials;

    @Value("${pluto.contract_addr.pluto}")
    private String plutoContractAddr;

    @Value("${pluto.aws.sqs.tx_queue}")
    private String txQueueName;

    private Subscription contractSubscription;

    @Autowired
    public BlockchainClientWeb3jImpl(ResourceLoader resourceLoader,
                                     TransactionService transactionService,
                                     WalletService walletService,
                                     @Value("${pluto.eth.client.url}") String ethClientUrl,
                                     @Value("${pluto.wallet.filepath}") String plutoWalletFilepath,
                                     @Value("${pluto.wallet.passphrase}") String plutoWalletPassphrase)
            throws IOException, CipherException {
        this.web3j = Web3j.build(new HttpService(ethClientUrl));

        this.transactionService = transactionService;
        this.walletService = walletService;

        Resource rootWalletResource = resourceLoader.getResource(plutoWalletFilepath);
        File rootWalletFile = rootWalletResource.getFile();

        this.credentials = WalletUtils.loadCredentials(plutoWalletPassphrase, rootWalletFile);
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void cleanUp() {
        if (this.contractSubscription != null && !this.contractSubscription.isUnsubscribed()) {
            this.contractSubscription.unsubscribe();
        }
    }

    @Override
    public String getClientVersion() {
        try {
            return this.web3j.web3ClientVersion().sendAsync().get().getWeb3ClientVersion();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    @JmsListener(destination = "${pluto.aws.sqs.tx_queue}")
    public void handleTxReq(String transactionIdStr) {
        try {
            Long transactionId = Long.valueOf(transactionIdStr);
            network.pluto.bibliotheca.models.Transaction transaction = this.transactionService.getTransaction(transactionId);
            if (transaction == null) {
                return;
            }

            JsonNode jsonNode = new ObjectMapper().readTree(transaction.getTransactionData());
            String txName = jsonNode.get("txName").asText();
            PlutoTxName plutoTxName = PlutoTxName.valueOf(txName);

            switch (plutoTxName) {
                case CREATE_WALLET:
                    PlutoCreateWalletTxRequest plutoCreateWalletTxRequest = PlutoCreateWalletTxRequest.fromJson(transaction.getTransactionData());


                    Pluto pluto = Pluto.load(this.plutoContractAddr, this.web3j, this.credentials,
                            ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
                    TransactionReceipt transactionReceipt = pluto.createWallet(new Uint256(plutoCreateWalletTxRequest.getMemberId())).get();
                    List<Pluto.WalletCreatedEventResponse> events = pluto.getWalletCreatedEvents(transactionReceipt);
                    if (events.size() != 0) {
                        Pluto.WalletCreatedEventResponse response = events.get(0);
                        this.walletService.registerAddress(response.memberId.getValue().longValue(), response.walletAddr.toString());
                        this.transactionService.updateTransactionStatus(transactionId, TransactionStatus.TX_SUCCESS);
                    }
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
