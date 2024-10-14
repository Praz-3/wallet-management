package com.crypto.tranaction.controller

import com.crypto.tranaction.dto.TransactionDetail
import com.crypto.tranaction.dto.TransactionRequest
import com.crypto.tranaction.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transaction")
class TransactionController(private val transactionService: TransactionService) {

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    fun sendTransaction(@RequestBody transactionRequest: TransactionRequest): TransactionDetail {
        return transactionService.performTransaction(transactionRequest)
    }

    @GetMapping("/history/{walletId}")
    fun getTransactionHistory(@PathVariable walletId: Long): List<TransactionDetail> {
        return transactionService.getTransactionHistory(walletId)
    }
}
