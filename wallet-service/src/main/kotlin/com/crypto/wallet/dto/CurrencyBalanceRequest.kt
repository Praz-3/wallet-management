package com.crypto.wallet.dto

import com.crypto.commons.enums.CurrencyType

data class CurrencyBalanceRequest(
    val currencyType: CurrencyType,
    val balance: Double
)