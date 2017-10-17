package network.pluto.alfred.components;

import lombok.extern.slf4j.Slf4j;
import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Wallet;
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
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class BlockchainClientWeb3jImpl implements BlockchainClient {
    private static final Logger logger = LoggerFactory.getLogger(BlockchainClientWeb3jImpl.class);

    private final Web3j web3j;
    private final Credentials credentials;
    private final JmsTemplate jmsTemplate;


    @Value("${pluto.contract_addr.pluto}")
    private String plutoContractAddr;

    @Value("${pluto.aws.sqs.tx_queue}")
    private String txQueueName;

    private Subscription contractSubscription;

    @Autowired
    public BlockchainClientWeb3jImpl(ResourceLoader resourceLoader,
                                     JmsTemplate jmsTemplate,
                                     @Value("${pluto.eth.client.url}") String ethClientUrl,
                                     @Value("${pluto.wallet.filepath}") String plutoWalletFilepath,
                                     @Value("${pluto.wallet.passphrase}") String plutoWalletPassphrase)
            throws IOException, CipherException {
        this.web3j = Web3j.build(new HttpService(ethClientUrl));

        this.jmsTemplate = jmsTemplate;

        Resource rootWalletResource = resourceLoader.getResource(plutoWalletFilepath);
        File rootWalletFile = rootWalletResource.getFile();

        this.credentials = WalletUtils.loadCredentials(plutoWalletPassphrase, rootWalletFile);
    }

    @PostConstruct
    public void init() {
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST, this.plutoContractAddr);
        this.contractSubscription = this.web3j.ethLogObservable(filter).subscribe(log -> logger.debug(log.toString()));
    }

    @PreDestroy
    public void cleanUp() {
        if (!this.contractSubscription.isUnsubscribed()) {
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

    @Override
    public Boolean sendCreateWalletReq(Member member, Wallet wallet) {
        return true;
    }
}
