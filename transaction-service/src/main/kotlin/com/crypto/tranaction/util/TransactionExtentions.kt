package com.crypto.tranaction.util

import com.crypto.tranaction.dto.TransactionDetail
import com.crypto.tranaction.model.Transaction

fun Transaction.toTransactionDetail(): TransactionDetail {
    return TransactionDetail(
        id,
        senderWalletId,
        recipientWalletId,
        cryptoCurrencyType,
        amount,
        timestamp,
        status,
        transactionId,
        exceptionMessage
    )
}