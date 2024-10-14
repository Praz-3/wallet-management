package com.crypto.tranaction.model

import com.crypto.tranaction.dto.TransactionRequest

object TransactionFactory {

    fun createTransaction(
        transactionRequest: TransactionRequest,
        transactionId: String,
        status: TransactionStatus
    ): Transaction {
        return with(transactionRequest) {
            Transaction(
                senderWalletId = senderWalletId,
                recipientWalletId = recipientWalletId,
                cryptoCurrencyType = cryptoCurrencyType,
                amount = amount,
                transactionId = transactionId,
                status = status
            )
        }
    }
}