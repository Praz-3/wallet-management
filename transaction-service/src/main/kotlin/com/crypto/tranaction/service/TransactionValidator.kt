package com.crypto.tranaction.service

import com.crypto.commons.dto.WalletDetail
import com.crypto.tranaction.model.Transaction
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class TransactionValidator {
    fun validateTransaction(senderWallet: WalletDetail, receiverWallet: WalletDetail, transaction: Transaction) {
        validateSenderBalance(senderWallet, transaction)
        validateReceiverCurrencySupport(receiverWallet, transaction)
    }

    private fun validateSenderBalance(senderWallet: WalletDetail, transaction: Transaction) {
        val senderBalance = senderWallet.currencyBalances.find { it.currencyType == transaction.cryptoCurrencyType }
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender's wallet does not support this currency")

        if (senderBalance.balance < transaction.amount) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance in sender's wallet.")
        }
    }

    private fun validateReceiverCurrencySupport(receiverWallet: WalletDetail, transaction: Transaction) {
        if (receiverWallet.currencyBalances.none { it.currencyType == transaction.cryptoCurrencyType }) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Receiver's wallet does not support this currency.")
        }
    }
}
