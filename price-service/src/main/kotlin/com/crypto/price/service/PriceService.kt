package com.crypto.price.service

import com.crypto.price.client.CryptoPriceClient
import com.crypto.price.client.WalletServiceClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PriceService(
    private val cryptoPriceClient: CryptoPriceClient,
    private val walletServiceClient: WalletServiceClient
) {
    private val logger: Logger = LoggerFactory.getLogger(PriceService::class.java)

    fun getTotalWalletValueInUSD(userId: Long): Mono<Double> {
        logger.debug("Received request to get total value of wallet for user: $userId")
        return walletServiceClient.getUserWallet(userId)
            .flatMap { wallet ->
                val priceRequests = wallet.currencyBalances.map { cryptoBalance ->
                    cryptoPriceClient.getCryptoPrice(cryptoBalance.currencyType.toString()).map { price ->
                        cryptoBalance.balance * price
                    }
                }

                Mono.zip(priceRequests) { prices ->
                    prices.sumOf { it as Double }
                }
            }
    }
}
