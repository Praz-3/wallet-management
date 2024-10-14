package com.crypto.wallet.model

import com.crypto.wallet.dto.WalletCreationRequest

object WalletFactory {
    fun createWallet(walletCreationRequest: WalletCreationRequest): Wallet {
        val currencyBalances = walletCreationRequest.initialBalances.map {
            CurrencyBalance(
                currencyType = it.currencyType,
                balance = it.balance,
                wallet = null
            )
        }.toMutableList()

        val wallet = with(walletCreationRequest) {
            Wallet.Builder()
                .userId(userId)
                .walletAddress(walletAddress)
                .privateKey(privateKey)
                .currencyBalances(currencyBalances)
                .build()
        }
        currencyBalances.forEach { it.wallet = wallet }
        return wallet
    }
}
