package com.crypto.price.client

import com.crypto.commons.dto.WalletDetail
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class WalletServiceClient {
    private val webClient = WebClient.builder().baseUrl("lb://WALLET-SERVICE").build()

    fun getUserWallet(userId: Long): Mono<WalletDetail> {
        return webClient.get()
            .uri("/wallet/user/$userId")
            .retrieve()
            .bodyToMono(WalletDetail::class.java)
    }
}
