package com.crypto.wallet.dto

import com.crypto.wallet.model.CurrencyType

data class WalletBalanceUpdateRequest(
    val walletId: Long,
    val currencyType: CurrencyType,
    val amount: Double
)