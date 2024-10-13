package com.crypto.tranaction.dto

import com.crypto.commons.enums.CurrencyType

data class TransactionRequest(
    val senderWalletId: Long,
    val recipientWalletId: Long,
    val cryptoCurrencyType: CurrencyType,
    val amount: Double
)