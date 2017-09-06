package network.pluto.alfred.services;

import network.pluto.alfred.components.BlockchainClient;
import network.pluto.alfred.models.User;
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

    @Autowired
    WalletService walletService;

    @Test
    public void testCreateWallet_NormalUser_ShouldReturnNormalResult() throws Exception {
        given(blockchainClient.createWallet(anyObject())).willReturn("mywalletaddress");

        User user = new User();
        user.setId(1L);
        user.setPassword("asdf");

        assertThat(this.walletService.createWallet(user)).isEqualTo("mywalletaddress");
    }
}
