package com.crypto.commons.dto

import com.crypto.commons.enums.CurrencyType

data class WalletBalanceUpdateRequest(
    val walletId: Long,
    val currencyType: CurrencyType,
    val amount: Double
)