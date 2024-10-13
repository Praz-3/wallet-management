package com.crypto.commons.dto

import com.crypto.commons.enums.CurrencyType

data class CurrencyBalanceDetail(
    val id: Long,
    val currencyType: CurrencyType,
    val balance: Double,
)
