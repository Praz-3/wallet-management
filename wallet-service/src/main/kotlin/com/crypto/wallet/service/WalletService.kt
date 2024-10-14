package com.crypto.wallet.service

import com.crypto.commons.dto.WalletBalanceUpdateRequest
import com.crypto.commons.dto.WalletDetail
import com.crypto.commons.enums.CurrencyType
import com.crypto.wallet.dto.WalletCreationRequest
import com.crypto.wallet.exception.CurrencyTypeNotFoundException
import com.crypto.wallet.exception.WalletNotFoundException
import com.crypto.wallet.model.CurrencyBalance
import com.crypto.wallet.model.Wallet
import com.crypto.wallet.model.WalletFactory
import com.crypto.wallet.model.WalletRepository
import com.crypto.wallet.utils.toWalletDetail
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for managing wallets and handling wallet-related operations such as
 * wallet creation, balance updates, and retrieval of wallet information.
 *
 * Features:
 *   - Create new wallets for users with specified initial balances.
 *   - Update balances of specific cryptocurrencies within a wallet.
 *   - Fetch wallet details for a given wallet ID or user ID.
 *   - Delete wallets from the database.
 */
@Service
class WalletService(private val walletRepository: WalletRepository) {
    private val logger: Logger = LoggerFactory.getLogger(WalletService::class.java)

    @Transactional
    fun createWallet(walletCreationRequest: WalletCreationRequest): WalletDetail {
        logger.debug("Request to Create a User Wallet received ...")
        val wallet = WalletFactory.createWallet(walletCreationRequest)
        return walletRepository.save(wallet).toWalletDetail().also { logger.debug("Wallet created successfully!") }
    }

    @Transactional
    fun updateWalletBalance(walletBalanceUpdateRequest: WalletBalanceUpdateRequest): WalletDetail {
        logger.debug("Received request to update wallet balance for ${walletBalanceUpdateRequest.walletId}")
        val wallet = getWalletByIdOrThrow(walletBalanceUpdateRequest.walletId)
        val currencyBalance = findCurrencyBalance(wallet, walletBalanceUpdateRequest.currencyType)

        updateCurrencyBalance(currencyBalance, walletBalanceUpdateRequest.amount)
        return walletRepository.save(wallet).toWalletDetail()
            .also { logger.debug("Wallet balance updated successfully!") }
    }

    fun getWalletById(walletId: Long): WalletDetail {
        return getWalletByIdOrThrow(walletId).toWalletDetail()
    }

    fun getUserWallet(userId: Long): WalletDetail {
        return walletRepository.findByUserId(userId)
            .orElseThrow { WalletNotFoundException("Wallet not found for userId: $userId") }
            .toWalletDetail()
    }

    @Transactional
    fun deleteWallet(walletId: Long) {
        if (walletRepository.existsById(walletId)) {
            logger.debug("Received request to delete wallet $walletId ...")
            walletRepository.deleteById(walletId)
            logger.debug("Wallet $walletId, deleted successfully")
        } else {
            throw WalletNotFoundException("Wallet not found with id: $walletId")
        }
    }

    private fun getWalletByIdOrThrow(walletId: Long): Wallet {
        return walletRepository.findById(walletId)
            .orElseThrow { WalletNotFoundException("Wallet not found with id: $walletId") }
    }

    private fun findCurrencyBalance(wallet: Wallet, currencyType: CurrencyType): CurrencyBalance {
        return wallet.currencyBalances.find { it.currencyType == currencyType }
            ?: throw CurrencyTypeNotFoundException("Currency type $currencyType not found in wallet")
    }

    private fun updateCurrencyBalance(currencyBalance: CurrencyBalance, amount: Double) {
        currencyBalance.balance += amount
    }
}
