package com.crypto.wallet.controller

import com.crypto.commons.dto.CurrencyBalanceDetail
import com.crypto.commons.dto.WalletBalanceUpdateRequest
import com.crypto.commons.dto.WalletDetail
import com.crypto.commons.enums.CurrencyType
import com.crypto.wallet.dto.CurrencyBalanceRequest
import com.crypto.wallet.dto.WalletCreationRequest
import com.crypto.wallet.service.WalletService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class WalletControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var walletController: WalletController
    private val objectMapper = ObjectMapper()
    private val walletService: WalletService = mock(WalletService::class.java)

    @BeforeEach
    fun setup() {
        walletController = WalletController(walletService)
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build()
    }

    @Test
    fun `should create wallet successfully`() {
        val walletCreationRequest = WalletCreationRequest(
            userId = 1L,
            walletAddress = "someAddress",
            privateKey = "somePrivateKey",
            initialBalances = listOf(
                CurrencyBalanceRequest(currencyType = CurrencyType.BITCOIN, balance = 0.0),
                CurrencyBalanceRequest(currencyType = CurrencyType.ETHEREUM, balance = 0.0)
            )
        )

        val walletDetail = WalletDetail(
            id = 1L,
            userId = walletCreationRequest.userId,
            walletAddress = walletCreationRequest.walletAddress,
            privateKey = walletCreationRequest.privateKey,
            currencyBalances = listOf(
                CurrencyBalanceDetail(currencyType = CurrencyType.BITCOIN, balance = 0.0),
                CurrencyBalanceDetail(currencyType = CurrencyType.ETHEREUM, balance = 0.0)
            )
        )

        // Mock the service to return a wallet detail
        `when`(walletService.createWallet(walletCreationRequest)).thenReturn(walletDetail)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/wallet/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(walletCreationRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.walletAddress").value(walletDetail.walletAddress))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(walletDetail.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(walletDetail.userId))
            .andExpect(MockMvcResultMatchers.jsonPath("$.privateKey").value(walletDetail.privateKey))
    }

    @Test
    fun `should update wallet balance successfully`() {
        val walletBalanceUpdateRequest = WalletBalanceUpdateRequest(
            walletId = 1L,
            currencyType = CurrencyType.BITCOIN,
            amount = 10.0
        )

        val walletDetail = WalletDetail(
            id = walletBalanceUpdateRequest.walletId,
            userId = 1L,
            walletAddress = "someAddress",
            privateKey = "somePrivateKey",
            currencyBalances = listOf(
                CurrencyBalanceDetail(currencyType = CurrencyType.BITCOIN, balance = 10.0)
            )
        )

        // Mock the service to return updated wallet detail
        `when`(walletService.updateWalletBalance(walletBalanceUpdateRequest)).thenReturn(walletDetail)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/wallet/update-balance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(walletBalanceUpdateRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.walletAddress").value(walletDetail.walletAddress))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(walletDetail.id))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.currencyBalances[0].currencyType").value(CurrencyType.BITCOIN.name)
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.currencyBalances[0].balance").value(10.0))
    }

    @Test
    fun `should get wallet details successfully`() {
        val walletId = 1L
        val walletDetail = WalletDetail(
            id = walletId,
            userId = 1L,
            walletAddress = "someAddress",
            privateKey = "somePrivateKey",
            currencyBalances = listOf(
                CurrencyBalanceDetail(currencyType = CurrencyType.BITCOIN, balance = 10.0)
            )
        )

        // Mock the service to return wallet detail
        `when`(walletService.getWalletById(walletId)).thenReturn(walletDetail)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet/detail/$walletId")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.walletAddress").value(walletDetail.walletAddress))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(walletDetail.id))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.currencyBalances[0].currencyType").value(CurrencyType.BITCOIN.name)
            )
    }

    @Test
    fun `should get user wallets successfully`() {
        val userId = 1L
        val walletDetail = WalletDetail(
            id = 1L,
            userId = userId,
            walletAddress = "someAddress",
            privateKey = "somePrivateKey",
            currencyBalances = listOf(
                CurrencyBalanceDetail(currencyType = CurrencyType.BITCOIN, balance = 10.0)
            )
        )

        // Mock the service to return wallet detail
        `when`(walletService.getUserWallet(userId)).thenReturn(walletDetail)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet/user/$userId")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.walletAddress").value(walletDetail.walletAddress))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(walletDetail.id))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.currencyBalances[0].currencyType").value(CurrencyType.BITCOIN.name)
            )
    }

    @Test
    fun `should delete wallet successfully`() {
        val walletId = 1L

        // Perform the delete request
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/wallet/delete/$walletId")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        // Verify that the delete method was called
        verify(walletService).deleteWallet(walletId)
    }
}
