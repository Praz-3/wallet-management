package com.crypto.commons.dto

import com.crypto.commons.enums.CurrencyType

data class WalletBalanceUpdateRequest(
    val walletId: Long = 0,
    val currencyType: CurrencyType = CurrencyType.BITCOIN,
    val amount: Double = 0.0
)