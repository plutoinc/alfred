package network.pluto.alfred.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import network.pluto.alfred.components.BlockchainClient;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class WalletServiceImpl implements WalletService {
    // TODO : load bucket name from configuration
    private static final String S3_BUCKET_NAME = "pluto-dev";

    private final BlockchainClient blockchainClient;
    private final AmazonS3 amazonS3;

    @Autowired
    public WalletServiceImpl(BlockchainClient blockchainClient, AmazonS3 amazonS3) {
        this.blockchainClient = blockchainClient;
        this.amazonS3 = amazonS3;
    }

    @Override
    public String createWallet(Long userId, String password) {
        byte[] walletContentBytes = this.blockchainClient.createWallet(password);
        InputStream walletContentInputStream = new ByteArrayInputStream(walletContentBytes);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        objectMetadata.setContentLength(walletContentBytes.length);

        PutObjectRequest putObjectRequest = new PutObjectRequest(S3_BUCKET_NAME,
                "wallet_" + String.valueOf(userId) + ".json", walletContentInputStream, objectMetadata);

        this.amazonS3.putObject(putObjectRequest);

        return "Created";
    }
}
