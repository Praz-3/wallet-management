package com.crypto.wallet.dto

data class WalletRequest(
    val userId: Long,
    val walletAddress: String,
    val cryptocurrencies: Set<String>,
    val privateKey: String
)
