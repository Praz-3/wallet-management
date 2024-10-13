package com.crypto.wallet.controller

import com.crypto.commons.dto.WalletBalanceUpdateRequest
import com.crypto.commons.dto.WalletDetail
import com.crypto.wallet.dto.WalletCreationRequest
import com.crypto.wallet.service.WalletService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/wallet")
class WalletController(private val walletService: WalletService) {

    @PostMapping("/create")
    fun createWallet(@RequestBody walletCreationRequest: WalletCreationRequest): ResponseEntity<WalletDetail> {
        val createdWallet = walletService.createWallet(walletCreationRequest)
        return ResponseEntity(createdWallet, HttpStatus.CREATED)
    }

    @PutMapping("/update-balance")
    fun updateWalletBalance(@RequestBody walletBalanceUpdateRequest: WalletBalanceUpdateRequest): ResponseEntity<WalletDetail> {
        val updatedWallet = walletService.updateWalletBalance(walletBalanceUpdateRequest)
        return ResponseEntity(updatedWallet, HttpStatus.OK)
    }

    @GetMapping("/detail/{walletId}")
    fun getWalletDetails(@PathVariable walletId: Long): ResponseEntity<WalletDetail> {
        val wallet = walletService.getWalletById(walletId)
        return ResponseEntity(wallet, HttpStatus.OK)
    }

    @GetMapping("/user/{userId}")
    fun getUserWallets(@PathVariable userId: Long): ResponseEntity<WalletDetail> {
        return ResponseEntity(walletService.getUserWallets(userId), HttpStatus.OK)
    }

    @DeleteMapping("/{walletId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteWallet(@PathVariable walletId: Long) {
        walletService.deleteWallet(walletId)
    }
}