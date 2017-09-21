package network.pluto.alfred.components;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import network.pluto.bibliotheca.models.Member;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.web3j.crypto.*;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

@Component
public class BlockchainClientWeb3jImpl implements BlockchainClient {
    private static final Logger logger = LoggerFactory.getLogger(BlockchainClientWeb3jImpl.class);

    @Value("${pluto.aws.s3.bucket_name}")
    private String s3BucketName;

    @Value("${pluto.wallet.key}")
    private String plutoWalletKey;

    @Value("${pluto.wallet.passphrase}")
    private String plutoWalletPassphrase;

    private final Web3j web3j;
    private final AmazonS3 amazonS3;
    private final Credentials credentials;

    @Autowired
    public BlockchainClientWeb3jImpl(ApplicationContext applicationContext, AmazonS3 amazonS3) throws IOException, CipherException {
        String gethUrl = applicationContext.getEnvironment().getProperty("pluto.geth.url");
        this.web3j = Web3j.build(new HttpService(gethUrl));

        this.amazonS3 = amazonS3;

        this.credentials = this.getCredentials();
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
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            WalletFile walletFile = Wallet.createStandard(member.getPassword(), ecKeyPair);

            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            String fileContentString = objectMapper.writeValueAsString(walletFile);

            byte[] fileContentBytes = fileContentString.getBytes(StandardCharsets.UTF_8);

            InputStream walletContentInputStream = new ByteArrayInputStream(fileContentBytes);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            objectMetadata.setContentLength(fileContentBytes.length);

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.s3BucketName,
                    walletFile.getAddress() + ".json", walletContentInputStream, objectMetadata);

            this.amazonS3.putObject(putObjectRequest);

            return walletFile.getAddress();
        } catch (InvalidAlgorithmParameterException |
                NoSuchAlgorithmException |
                NoSuchProviderException |
                CipherException |
                JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
        }

        return null;
    }

    private Credentials getCredentials() throws IOException, CipherException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(this.s3BucketName,
                this.plutoWalletKey);
        S3Object s3Object = this.amazonS3.getObject(getObjectRequest);
        InputStream inputStream = s3Object.getObjectContent();
        String walletString = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));

        return WalletUtils.loadCredentials(this.plutoWalletPassphrase, walletString);
    }
}
