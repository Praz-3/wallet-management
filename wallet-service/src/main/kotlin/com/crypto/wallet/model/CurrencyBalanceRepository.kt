package com.crypto.wallet.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyBalanceRepository : JpaRepository<CurrencyBalance, Long>