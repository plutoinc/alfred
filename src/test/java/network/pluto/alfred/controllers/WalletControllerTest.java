package network.pluto.alfred.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.pluto.alfred.models.User;
import network.pluto.alfred.services.WalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @MockBean
    private WalletService walletService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateWallet_NormalWalletCreation_ShouldReturnNormalResult() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setPassword("asdf");

        given(this.walletService.createWallet(anyObject())).willReturn("mynewwalletaddr");

        this.mockMvc.perform(post("/wallet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(
                        content().json(
                                "{\"id\":1,\"password\":\"asdf\",\"walletAddress\":\"mynewwalletaddr\"}"));
    }

    @Test
    public void testCreateWallet_InvalidJsonRequestBody_ShouldReturnError() throws Exception {
        User userWithoutPassword = new User();
        userWithoutPassword.setId(1L);

        this.mockMvc.perform(post("/wallet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(userWithoutPassword)))
                .andExpect(status().isBadRequest());

        User userWithoutId = new User();
        userWithoutId.setPassword("asdf");

        this.mockMvc.perform(post("/wallet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(userWithoutId)))
                .andExpect(status().isBadRequest());
    }
}
