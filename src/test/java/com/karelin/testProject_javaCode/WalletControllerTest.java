package com.karelin.testProject_javaCode;

import com.karelin.testProject_javaCode.controller.WalletController;
import com.karelin.testProject_javaCode.model.Wallet;
import com.karelin.testProject_javaCode.model.WalletUpdater;
import com.karelin.testProject_javaCode.service.WalletService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@WebMvcTest(WalletController.class)
class WalletControllerTest {
	@MockBean
    private WalletService walletService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnListOfWallets() throws Exception {
        UUID uuidEx = UUID.randomUUID();
        when(walletService.getAllWallets())
                .thenReturn(List.of(new Wallet(uuidEx, 100.00)));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/wallets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(100.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(uuidEx.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldCreateWallet() throws Exception {

        UUID uuidEx = UUID.randomUUID();
        Wallet wallet = new Wallet(null, 100.00);
        wallet.setId(uuidEx);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/createwallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":"
                                + wallet.getAmount()
                                + ", \"id\": \""
                                + wallet.getId()
                                + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString(uuidEx.toString())));

        verify(walletService).saveWallet(any(Wallet.class));
    }

    @Test
    void ShouldWithdrawMoney() throws Exception {
        UUID uuidEx = UUID.randomUUID();
        Wallet wallet = new Wallet(null, 1000.00);
        wallet.setId(uuidEx);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"valletId\":\""
                            + wallet.getId()
                            + "\", \"operationType\": \"withdraw\", \"amount\": 500}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(walletService).updateWalletAmount(any(WalletUpdater.class));
    }
}
