package network.pluto.alfred.components;

import com.amazonaws.services.s3.AmazonS3;
import lombok.extern.slf4j.Slf4j;
import network.pluto.bibliotheca.models.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class BlockchainClientWeb3jImpl implements BlockchainClient {
    private static final Logger logger = LoggerFactory.getLogger(BlockchainClientWeb3jImpl.class);

    private final Web3j web3j;
    private final Credentials credentials;
    private final JmsTemplate jmsTemplate;

    private final Map<String, Subscription> subscriptionMap = new ConcurrentHashMap<>();

    @Value("${pluto.contract_addr.pluto}")
    private String plutoContractAddr;

    @Value("${pluto.aws.sqs.tx_queue}")
    private String txQueueName;

    @Autowired
    public BlockchainClientWeb3jImpl(ResourceLoader resourceLoader,
                                     JmsTemplate jmsTemplate,
                                     @Value("${pluto.geth.url}") String gethUrl,
                                     @Value("${pluto.wallet.filepath}") String plutoWalletFilepath,
                                     @Value("${pluto.wallet.passphrase}") String plutoWalletPassphrase)
            throws IOException, CipherException {
        this.web3j = Web3j.build(new HttpService(gethUrl));

        this.jmsTemplate = jmsTemplate;

        Resource rootWalletResource = resourceLoader.getResource(plutoWalletFilepath);
        File rootWalletFile = rootWalletResource.getFile();

        this.credentials = WalletUtils.loadCredentials(plutoWalletPassphrase, rootWalletFile);
    }

    @Override
    public String getClientVersion() {
        try {
            return this.web3j.web3ClientVersion().sendAsync().get().getWeb3ClientVersion();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    @Override
    public String createWallet(Member member) {
//        Pluto pluto = Pluto.load(this.plutoContractAddr, this.web3j, this.credentials,
//                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
//
//        Subscription subscription = pluto.walletCreatedEventObservable(
//                DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
//                .subscribe(BlockchainClientWeb3jImpl::walletCreated);
//
//        try {
//            TransactionReceipt transactionReceipt = pluto.createWallet(new Uint256(member.getMemberId())).get();
//
//            this.subscriptionMap.put(transactionReceipt.getTransactionHash(), subscription);
//        } catch (InterruptedException | ExecutionException e) {
//            logger.error(e.getLocalizedMessage());
//        }

        // TODO: send JSON object {"tx_type": "crate_wallet", "tx_hash": "0xc0ffee"}
        this.jmsTemplate.convertAndSend(this.txQueueName, "hello");

        return "wallet";
    }
}
