package com.crypto.wallet.controller

import com.crypto.commons.dto.WalletBalanceUpdateRequest
import com.crypto.commons.dto.WalletDetail
import com.crypto.wallet.dto.WalletCreationRequest
import com.crypto.wallet.service.WalletService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/wallet")
class WalletController(private val walletService: WalletService) {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun createWallet(@RequestBody walletCreationRequest: WalletCreationRequest): WalletDetail {
        return walletService.createWallet(walletCreationRequest)
    }

    @PutMapping("/update-balance")
    fun updateWalletBalance(@RequestBody walletBalanceUpdateRequest: WalletBalanceUpdateRequest): WalletDetail {
        return walletService.updateWalletBalance(walletBalanceUpdateRequest)
    }

    @GetMapping("/detail/{walletId}")
    fun getWalletDetails(@PathVariable walletId: Long): WalletDetail {
        return walletService.getWalletById(walletId)
    }

    @GetMapping("/user/{userId}")
    fun getUserWallets(@PathVariable userId: Long): WalletDetail {
        return walletService.getUserWallets(userId)
    }

    @DeleteMapping("/{walletId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteWallet(@PathVariable walletId: Long) {
        walletService.deleteWallet(walletId)
    }
}