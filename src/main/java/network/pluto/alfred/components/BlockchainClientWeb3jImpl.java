package network.pluto.alfred.components;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.*;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

@Component
public class BlockchainClientWeb3jImpl implements BlockchainClient {
    private static final Logger logger = LoggerFactory.getLogger(BlockchainClientWeb3jImpl.class);

    private final Web3j web3j;

    public BlockchainClientWeb3jImpl() {
        this.web3j = Web3j.build(new HttpService());
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
    public byte[] createWallet(String password) {
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            WalletFile walletFile = Wallet.createStandard(password, ecKeyPair);

            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            String fileContentString = objectMapper.writeValueAsString(walletFile);
            return fileContentString.getBytes(StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException |
                NoSuchAlgorithmException |
                NoSuchProviderException |
                CipherException |
                JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
        }

        return null;
    }
}
