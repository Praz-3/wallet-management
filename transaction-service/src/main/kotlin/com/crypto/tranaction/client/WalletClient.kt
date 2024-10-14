package com.crypto.tranaction.client

import com.crypto.commons.dto.WalletBalanceUpdateRequest
import com.crypto.commons.dto.WalletDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

@Component
class WalletClient(private val restTemplate: RestTemplate) {

    @Value("\${wallet.service.name}")
    private lateinit var walletServiceName: String

    fun fetchWallet(walletId: Long): WalletDetail {
        val walletServiceUrl = "http://$walletServiceName/api/wallet/detail/$walletId"
        return try {
            restTemplate.getForObject(walletServiceUrl, WalletDetail::class.java)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found")
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch wallet: ${e.message}")
        }
    }

    fun updateWalletBalance(walletBalanceUpdateRequest: WalletBalanceUpdateRequest) {
        val walletServiceUrl = "http://$walletServiceName/api/wallet/update-balance"

        try {
            restTemplate.put(walletServiceUrl, walletBalanceUpdateRequest)
        } catch (e: Exception) {
            throw RuntimeException("Failed to update wallet balance: ${e.message}")
        }
    }
}
