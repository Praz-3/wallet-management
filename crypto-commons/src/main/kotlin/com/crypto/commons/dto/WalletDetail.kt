package com.crypto.commons.dto

data class WalletDetail(
    val id: Long = 0,
    val userId: Long = 0,
    val walletAddress: String = "",
    val currencyBalances: List<CurrencyBalanceDetail> = listOf(),
    val privateKey: String = ""
)
