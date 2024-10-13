package com.crypto.price.client

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class CryptoPriceClient {

    private val webClient = WebClient.builder()
        .baseUrl("https://api.coingecko.com/api/v3")
        .build()

    fun getCryptoPrice(cryptoCurrencyType: String): Mono<Double> {
        return webClient.get()
            .uri("/simple/price?ids=$cryptoCurrencyType&vs_currencies=usd")
            .retrieve()
            .bodyToMono(Map::class.java)
            .map { response ->
                val cryptoPrices = response[cryptoCurrencyType] as? Map<*, *> ?: throw Exception("Invalid response format")
                cryptoPrices["usd"] as? Double ?: throw Exception("Price not found for $cryptoCurrencyType in USD")
            }
    }
}
