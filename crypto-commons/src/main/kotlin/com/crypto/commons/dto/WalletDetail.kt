package com.crypto.commons.dto

data class WalletDetail(
    val id: Long,
    val userId: Long,
    val walletAddress: String,
    val currencyBalances: List<CurrencyBalanceDetail>,
    val privateKey: String
)
