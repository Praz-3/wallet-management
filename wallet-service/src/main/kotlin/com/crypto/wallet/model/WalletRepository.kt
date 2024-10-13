package com.crypto.wallet.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface WalletRepository : JpaRepository<Wallet, Long> {
    fun findByUserId(userId: Long): Optional<Wallet>
}