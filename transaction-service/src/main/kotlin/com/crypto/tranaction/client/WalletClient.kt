package com.crypto.tranaction.client

import com.crypto.commons.dto.WalletBalanceUpdateRequest
import com.crypto.commons.dto.WalletDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class WalletClient(
    @Value("\${wallet.service.name}") private val walletServiceName: String,
    private val webClientBuilder: WebClient.Builder
) {

    private val webClient: WebClient = webClientBuilder.baseUrl("http://$walletServiceName/api/wallet").build()

    fun fetchWallet(walletId: Long): WalletDetail {
        return webClient.get()
            .uri("/detail/$walletId")
            .retrieve()
            .bodyToMono(WalletDetail::class.java)
            .block() ?: throw Exception("Wallet Not Found for $walletId")
    }

    fun updateWalletBalance(walletBalanceUpdateRequest: WalletBalanceUpdateRequest): WalletDetail {
        return webClient.put()
            .uri("/update-balance")
            .bodyValue(walletBalanceUpdateRequest)
            .retrieve()
            .bodyToMono(WalletDetail::class.java)
            .block()
            ?: throw RuntimeException("Failed to update wallet balance: ${walletBalanceUpdateRequest.walletId}")
    }
}