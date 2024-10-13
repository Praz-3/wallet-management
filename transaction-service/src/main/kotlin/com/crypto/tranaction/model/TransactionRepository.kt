package com.crypto.tranaction.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findBySenderWalletIdOrRecipientWalletId(walletId: Long, walletId2: Long): List<Transaction>
}
