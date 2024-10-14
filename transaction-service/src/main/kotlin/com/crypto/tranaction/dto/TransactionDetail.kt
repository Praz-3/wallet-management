package com.crypto.tranaction.dto

import com.crypto.commons.enums.CurrencyType
import com.crypto.tranaction.model.TransactionStatus
import java.time.LocalDateTime

data class TransactionDetail(
    val id: Long = 0,
    val senderWalletId: Long = 0,
    val recipientWalletId: Long = 0,
    val cryptoCurrencyType: CurrencyType = CurrencyType.BITCOIN,
    val amount: Double = 0.0,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    var status: TransactionStatus = TransactionStatus.PENDING,
    var transactionId: String = "",
    var exceptionMessage: String? = null
)
