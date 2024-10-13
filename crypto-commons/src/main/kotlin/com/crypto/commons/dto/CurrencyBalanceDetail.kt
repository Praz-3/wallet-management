package com.crypto.wallet.dto

import com.crypto.wallet.model.CurrencyType

data class CurrencyBalanceDetail(
    val id: Long,
    val currencyType: CurrencyType,
    val balance: Double,
)
