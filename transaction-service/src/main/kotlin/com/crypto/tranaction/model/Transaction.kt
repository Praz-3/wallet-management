package com.crypto.tranaction.model

import com.crypto.commons.enums.CurrencyType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val senderWalletId: Long = 0,

    @Column(nullable = false)
    val recipientWalletId: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val cryptoCurrencyType: CurrencyType = CurrencyType.BITCOIN,

    @Column(nullable = false)
    val amount: Double = 0.0,

    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: TransactionStatus = TransactionStatus.PENDING,

    @Column(nullable = false)
    var transactionId: String = "",

    @Column(nullable = true)
    var exceptionMessage: String? = null
)
