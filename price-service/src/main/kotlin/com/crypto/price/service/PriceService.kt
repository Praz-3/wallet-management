package com.crypto.price.service

import com.crypto.price.client.CryptoPriceClient
import com.crypto.price.client.WalletServiceClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PriceService(
    private val cryptoPriceClient: CryptoPriceClient,
    private val walletServiceClient: WalletServiceClient
) {

    fun getTotalWalletValueInUSD(userId: Long): Mono<Double> {
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
