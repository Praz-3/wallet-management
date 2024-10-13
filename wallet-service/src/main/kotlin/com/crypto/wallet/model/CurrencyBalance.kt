package com.crypto.wallet.model

import com.crypto.commons.enums.CurrencyType
import jakarta.persistence.*

@Entity
@Table(name = "currency_balances")
data class CurrencyBalance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val currencyType: CurrencyType,

    @Column(nullable = false)
    var balance: Double = 0.0,

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    var wallet: Wallet?
)
