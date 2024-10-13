package com.crypto.tranaction.dto

import com.crypto.commons.enums.CurrencyType
import com.crypto.tranaction.model.TransactionStatus
import java.time.LocalDateTime

data class TransactionDetail(
    val id: Long,
    val senderWalletId: Long,
    val recipientWalletId: Long,
    val cryptoCurrencyType: CurrencyType,
    val amount: Double,
    val timestamp: LocalDateTime,
    var status: TransactionStatus,
    var transactionId: String
)
