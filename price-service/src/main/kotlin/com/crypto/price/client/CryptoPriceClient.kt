package com.crypto.price.client

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient

@Component
class CryptoPriceClient(private val webClient: WebClient) {

    private val logger = LoggerFactory.getLogger(CryptoPriceClient::class.java)

    fun getCryptoPrice(cryptoCurrencyType: String): Mono<Double> {
        return webClient.get()
            .uri("https://api.coingecko.com/api/v3/simple/price?ids=$cryptoCurrencyType&vs_currencies=usd")
            .retrieve()
            .bodyToMono(Map::class.java)
            .doOnNext { response ->
                logger.debug("API Response: {}", response)
            }
            .map { response ->
                val cryptoPrices = response[cryptoCurrencyType.lowercase()] as? Map<*, *>
                    ?: throw Exception("Invalid response format")
                val priceInDouble = when (val price = cryptoPrices["usd"]) {
                    is Double -> price
                    is Int -> price.toDouble()
                    else -> throw Exception("Price not found for $cryptoCurrencyType in USD")
                }
                priceInDouble
            }
    }
}
