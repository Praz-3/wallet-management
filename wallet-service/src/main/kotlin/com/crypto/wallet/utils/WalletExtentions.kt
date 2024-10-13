package com.crypto.wallet.utils

import com.crypto.commons.dto.CurrencyBalanceDetail
import com.crypto.commons.dto.WalletDetail
import com.crypto.wallet.model.CurrencyBalance
import com.crypto.wallet.model.Wallet

fun Wallet.toWalletDetail(): WalletDetail {
    return WalletDetail(
        id = this.id,
        userId = this.userId,
        walletAddress = this.walletAddress,
        currencyBalances = this.currencyBalances.map { it.toCurrencyBalanceDetail() },
        privateKey = this.privateKey
    )
}

fun CurrencyBalance.toCurrencyBalanceDetail(): CurrencyBalanceDetail {
    return CurrencyBalanceDetail(
        id = this.id,
        currencyType = this.currencyType,
        balance = this.balance
    )
}