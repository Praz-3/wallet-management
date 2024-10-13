package com.crypto.wallet.dto

data class WalletCreationRequest(
    val userId: Long,
    val walletAddress: String,
    val privateKey: String,
    val initialBalances: List<CurrencyBalanceRequest>
)
