package com.crypto.tranaction.controller

import com.crypto.tranaction.dto.TransactionDetail
import com.crypto.tranaction.dto.TransactionRequest
import com.crypto.tranaction.exception.TransactionException
import com.crypto.tranaction.model.TransactionStatus
import com.crypto.tranaction.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transaction")
class TransactionController(private val transactionService: TransactionService) {

    @PostMapping("/execute")
    @ResponseStatus(HttpStatus.CREATED)
    fun executeTransaction(@RequestBody transactionRequest: TransactionRequest): TransactionDetail {
        val transaction = transactionService.performTransaction(transactionRequest)
        if (transaction.status == TransactionStatus.FAILED) {
            throw TransactionException(transaction.exceptionMessage ?: "Transaction Failed!")
        }
        return transaction
    }

    @GetMapping("/history/{walletId}")
    fun getTransactionHistory(@PathVariable walletId: Long): List<TransactionDetail> {
        return transactionService.getTransactionHistory(walletId)
    }
}
