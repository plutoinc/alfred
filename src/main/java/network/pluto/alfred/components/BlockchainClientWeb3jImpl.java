package network.pluto.alfred.components;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import network.pluto.alfred.transactions.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@Slf4j
@Getter
@Component
public class BlockchainClientWeb3jImpl implements BlockchainClient {

    private final Web3j web3j;
    private final Credentials credentials;

    @Value("${pluto.contract_addr.pluto}")
    private String plutoContractAddr;

    @Value("${pluto.aws.sqs.tx_queue}")
    private String txQueueName;

    private Subscription contractSubscription;

    @Autowired
    public BlockchainClientWeb3jImpl(ResourceLoader resourceLoader,
                                     @Value("${pluto.eth.client.url}") String ethClientUrl,
                                     @Value("${pluto.wallet.filepath}") String plutoWalletFilepath,
                                     @Value("${pluto.wallet.passphrase}") String plutoWalletPassphrase)
            throws IOException, CipherException {
        this.web3j = Web3j.build(new HttpService(ethClientUrl));

        Resource rootWalletResource = resourceLoader.getResource(plutoWalletFilepath);
        InputStream inputStream = rootWalletResource.getInputStream();

        WalletFile walletFile = JsonUtil.getMapper().readValue(inputStream, WalletFile.class);
        this.credentials = Credentials.create(Wallet.decrypt(plutoWalletPassphrase, walletFile));
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
}
