package com.crypto.tranaction.dto

import com.crypto.commons.enums.CurrencyType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class TransactionDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val senderWalletId: Long,

    @Column(nullable = false)
    val recipientWalletId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val cryptoCurrencyType: CurrencyType,

    @Column(nullable = false)
    val amount: Double,

    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: TransactionStatus = TransactionStatus.PENDING,

    @Column(nullable = false)
    var transactionId: String
)
