package com.crypto.price.client

import com.crypto.commons.dto.WalletDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class WalletServiceClient(
    @Value("\${wallet.service.name}") private val walletServiceName: String
) {
    private val webClient: WebClient = WebClient.builder()
        .baseUrl("http://$walletServiceName")
        .build()

    fun getUserWallet(userId: Long): Mono<WalletDetail> {
        return webClient.get()
            .uri("/api/wallet/user/$userId")
            .retrieve()
            .bodyToMono(WalletDetail::class.java)
    }
}
