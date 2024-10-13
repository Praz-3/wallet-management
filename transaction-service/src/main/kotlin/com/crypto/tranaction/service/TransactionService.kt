package com.crypto.tranaction.service

import com.crypto.commons.dto.WalletBalanceUpdateRequest
import com.crypto.tranaction.client.WalletClient
import com.crypto.tranaction.dto.TransactionDetail
import com.crypto.tranaction.dto.TransactionRequest
import com.crypto.tranaction.model.Transaction
import com.crypto.tranaction.model.TransactionRepository
import com.crypto.tranaction.model.TransactionStatus
import com.crypto.tranaction.util.toTransactionDetail
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val walletClient: WalletClient
) {

    @Transactional
    fun performTransaction(transactionRequest: TransactionRequest): TransactionDetail {
        // Third party transaction ID
        val transactionId = UUID.randomUUID().toString()
        val transaction = with(transactionRequest) {
            Transaction(
                senderWalletId = senderWalletId,
                recipientWalletId = recipientWalletId,
                cryptoCurrencyType = cryptoCurrencyType,
                amount = amount,
                transactionId = transactionId,
                status = TransactionStatus.PENDING
            )
        }
        val savedTransaction = transactionRepository.save(transaction)
        simulateBlockchainTransaction(savedTransaction)
        return savedTransaction.toTransactionDetail()
    }

    private fun simulateBlockchainTransaction(transaction: Transaction) {
        try {
            val senderWallet = walletClient.fetchWallet(transaction.senderWalletId)
            val receiverWallet = walletClient.fetchWallet(transaction.recipientWalletId)

            val senderBalance =
                senderWallet.currencyBalances.find { it.currencyType == transaction.cryptoCurrencyType }
                    ?: throw ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Sender's wallet does not support this currency"
                    )

            if (senderBalance.balance < transaction.amount) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance in sender's wallet.")
            }

            if (receiverWallet.currencyBalances.none { it.currencyType == transaction.cryptoCurrencyType }) {
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Receiver's wallet does not support this currency."
                )
            }

            // Debit from sender's wallet
            walletClient.updateWalletBalance(
                WalletBalanceUpdateRequest(
                    transaction.senderWalletId,
                    transaction.cryptoCurrencyType,
                    -transaction.amount
                )
            )

            // Credit to receiver's wallet
            walletClient.updateWalletBalance(
                WalletBalanceUpdateRequest(
                    transaction.recipientWalletId,
                    transaction.cryptoCurrencyType,
                    transaction.amount
                )
            )
            transaction.status = TransactionStatus.COMPLETED
        } catch (e: Exception) {
            transaction.status = TransactionStatus.FAILED
        } finally {
            transactionRepository.save(transaction)
        }
    }

    fun getTransactionHistory(walletId: Long): List<TransactionDetail> {
        return transactionRepository.findBySenderWalletIdOrRecipientWalletId(walletId, walletId)
            .map { it.toTransactionDetail() }
    }
}
