package com.crypto.commons.dto

import com.crypto.commons.enums.CurrencyType

data class CurrencyBalanceDetail(
    val id: Long = 0,
    val currencyType: CurrencyType = CurrencyType.BITCOIN,
    val balance: Double = 0.0
)
