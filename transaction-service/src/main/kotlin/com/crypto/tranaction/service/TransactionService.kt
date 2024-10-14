package com.crypto.tranaction.service

import com.crypto.commons.dto.WalletBalanceUpdateRequest
import com.crypto.commons.dto.WalletDetail
import com.crypto.tranaction.client.WalletClient
import com.crypto.tranaction.dto.TransactionDetail
import com.crypto.tranaction.dto.TransactionRequest
import com.crypto.tranaction.model.Transaction
import com.crypto.tranaction.model.TransactionFactory
import com.crypto.tranaction.model.TransactionRepository
import com.crypto.tranaction.model.TransactionStatus
import com.crypto.tranaction.util.toTransactionDetail
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * Service class for managing cryptocurrency transactions between wallets.
 *
 * The `TransactionService` handles the logic for processing transactions, verifying wallet balances,
 * debiting and crediting amounts, and maintaining transaction history.
 * It interacts with the `WalletClient` to fetch wallet information and update balances in external wallet services.
 */
@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val walletClient: WalletClient,
    private val transactionValidator: TransactionValidator
) {
    private val logger: Logger = LoggerFactory.getLogger(TransactionService::class.java)

    @Transactional
    fun performTransaction(transactionRequest: TransactionRequest): TransactionDetail {
        val transactionId = UUID.randomUUID().toString()
        val transaction =
            TransactionFactory.createTransaction(transactionRequest, transactionId, TransactionStatus.PENDING)
        val savedTransaction = transactionRepository.save(transaction)

        try {
            processTransaction(savedTransaction)
            savedTransaction.status = TransactionStatus.COMPLETED
            logger.debug("Transaction ${transaction.transactionId} completed successfully!")
        } catch (e: Exception) {
            savedTransaction.status = TransactionStatus.FAILED
            savedTransaction.exceptionMessage = e.message
            logger.error("Transaction ${transaction.transactionId} failed!", e)
        }
        return transactionRepository.save(savedTransaction).toTransactionDetail()
    }

    private fun processTransaction(transaction: Transaction) {
        val senderWallet = walletClient.fetchWallet(transaction.senderWalletId)
        val receiverWallet = walletClient.fetchWallet(transaction.recipientWalletId)
        transactionValidator.validateTransaction(senderWallet, receiverWallet, transaction)
        executeDebit(senderWallet, transaction)
        executeCredit(receiverWallet, transaction)
    }

    private fun executeDebit(wallet: WalletDetail, transaction: Transaction) {
        walletClient.updateWalletBalance(
            WalletBalanceUpdateRequest(
                walletId = wallet.id,
                currencyType = transaction.cryptoCurrencyType,
                amount = -transaction.amount
            )
        )
    }

    private fun executeCredit(wallet: WalletDetail, transaction: Transaction) {
        walletClient.updateWalletBalance(
            WalletBalanceUpdateRequest(
                walletId = wallet.id,
                currencyType = transaction.cryptoCurrencyType,
                amount = transaction.amount
            )
        )
    }

    fun getTransactionHistory(walletId: Long): List<TransactionDetail> {
        return transactionRepository.findBySenderWalletIdOrRecipientWalletId(walletId, walletId)
            .map { it.toTransactionDetail() }
    }
}
