package com.crypto.tranaction.controller

import com.crypto.commons.enums.CurrencyType
import com.crypto.tranaction.dto.TransactionDetail
import com.crypto.tranaction.dto.TransactionRequest
import com.crypto.tranaction.model.TransactionStatus
import com.crypto.tranaction.service.TransactionService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime

class TransactionControllerTest {

    private lateinit var mockMvc: MockMvc
    private val transactionService = mock(TransactionService::class.java)
    private lateinit var transactionController: TransactionController

    @BeforeEach
    fun setUp() {
        transactionController = TransactionController(transactionService)
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build()
    }

    @Test
    fun `executeTransaction should return TransactionDetail when transaction is successful`() {
        // Given
        val transactionRequest = TransactionRequest(1L, 2L, CurrencyType.BITCOIN, 0.1)
        val expectedTransactionDetail = TransactionDetail(
            id = 1L,
            senderWalletId = 1L,
            recipientWalletId = 2L,
            cryptoCurrencyType = CurrencyType.BITCOIN,
            amount = 0.1,
            timestamp = LocalDateTime.now(),
            status = TransactionStatus.COMPLETED,
            transactionId = "12345",
            exceptionMessage = null
        )

        // Mock the service response
        `when`(transactionService.performTransaction(transactionRequest)).thenReturn(expectedTransactionDetail)

        // When
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/transaction/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"senderWalletId":1,"recipientWalletId":2,"cryptoCurrencyType":"BITCOIN","amount":0.1}""")
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `getTransactionHistory should return list of TransactionDetail`() {
        // Given
        val walletId = 1L
        val transactionDetails = listOf(
            TransactionDetail(
                1L,
                1L,
                2L,
                CurrencyType.BITCOIN,
                0.1,
                LocalDateTime.now(),
                TransactionStatus.COMPLETED,
                "12345",
                null
            )
        )

        // Mock the service response
        `when`(transactionService.getTransactionHistory(walletId)).thenReturn(transactionDetails)

        // When & Then
        val response = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/transaction/history/$walletId")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}
