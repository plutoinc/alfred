package network.pluto.alfred.services;

import network.pluto.alfred.components.BlockchainClient;
import network.pluto.alfred.models.User;
import network.pluto.alfred.models.Wallet;
import network.pluto.alfred.repositories.WalletRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletServiceTest {
    @MockBean
    BlockchainClient blockchainClient;

    @MockBean
    WalletRepository walletRepository;

    @Autowired
    WalletService walletService;

    @Test
    public void testCreateWallet_NormalUser_ShouldReturnNormalResult() throws Exception {
        Wallet wallet = new Wallet();
        wallet.setAddress("mywalletaddress");
        given(walletRepository.save((Wallet)anyObject())).willReturn(wallet);
        given(blockchainClient.createWallet(anyObject())).willReturn("mywalletaddress");

        User user = new User();
        user.setId(1L);
        user.setPassword("asdf");

        Wallet result = this.walletService.createWallet(user);
        System.out.println(result);

        assertThat(this.walletService.createWallet(user)).hasFieldOrPropertyWithValue("address", "mywalletaddress");
    }
}
