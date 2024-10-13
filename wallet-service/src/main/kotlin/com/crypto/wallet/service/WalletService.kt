package com.crypto.wallet.service

import com.crypto.commons.dto.WalletBalanceUpdateRequest
import com.crypto.commons.dto.WalletDetail
import com.crypto.wallet.dto.WalletCreationRequest
import com.crypto.wallet.model.CurrencyBalance
import com.crypto.wallet.model.Wallet
import com.crypto.wallet.model.WalletRepository
import com.crypto.wallet.utils.toWalletDetail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WalletService(private val walletRepository: WalletRepository) {

    @Transactional
    fun createWallet(walletCreationRequest: WalletCreationRequest): WalletDetail {
        val currencyBalances = walletCreationRequest.initialBalances.map {
            CurrencyBalance(
                currencyType = it.currencyType,
                balance = it.balance,
                wallet = null
            )
        }.toMutableList()

        val wallet = with(walletCreationRequest) {
            Wallet.Builder()
                .userId(userId)
                .walletAddress(walletAddress)
                .privateKey(privateKey)
                .currencyBalances(currencyBalances)
                .build()
        }
        currencyBalances.forEach { it.wallet = wallet }
        return walletRepository.save(wallet).toWalletDetail()
    }

    @Transactional
    fun updateWalletBalance(walletBalanceUpdateRequest: WalletBalanceUpdateRequest): WalletDetail {
        val wallet = walletRepository.findById(walletBalanceUpdateRequest.walletId)
            .orElseThrow { Exception("Wallet Not Found!") }
        val currencyBalance = wallet.currencyBalances.find { balance ->
            balance.currencyType == walletBalanceUpdateRequest.currencyType
        }
        if (currencyBalance != null) {
            currencyBalance.balance += walletBalanceUpdateRequest.amount
        } else {
            throw Exception("Currency type not found in wallet.")
        }
        return walletRepository.save(wallet).toWalletDetail()
    }

    fun getWalletById(walledId: Long): WalletDetail {
        return walletRepository.findById(walledId).orElseThrow { Exception("Wallet Not Found!") }.toWalletDetail()
    }

    fun getUserWallets(userId: Long): WalletDetail {
        return walletRepository.findByUserId(userId).orElseThrow { Exception("Wallet Not Found!") }.toWalletDetail()
    }

    fun deleteWallet(walletId: Long) {
        walletRepository.deleteById(walletId)
    }
}
