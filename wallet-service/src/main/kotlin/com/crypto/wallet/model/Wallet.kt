package com.crypto.wallet.model

import com.crypto.wallet.utils.EncryptionUtils
import jakarta.persistence.*

@Entity
@Table(name = "wallets")
data class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val userId: Long = 0,

    @Column(nullable = false)
    var walletAddress: String = "",

    @OneToMany(mappedBy = "wallet", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val currencyBalances: MutableList<CurrencyBalance> = mutableListOf(),

    @Column(nullable = false)
    var privateKey: String = ""
) {
    class Builder {
        private var userId: Long = 0
        private var walletAddress: String = ""
        private var currencyBalances: MutableList<CurrencyBalance> = mutableListOf()
        private var privateKey: String = ""

        fun userId(userId: Long) = apply { this.userId = userId }
        fun walletAddress(walletAddress: String) = apply { this.walletAddress = walletAddress }
        fun currencyBalances(currencyBalances: List<CurrencyBalance>) =
            apply { this.currencyBalances = currencyBalances.toMutableList() }

        fun privateKey(privateKey: String) = apply { this.privateKey = privateKey }

        fun build(): Wallet {
            val encryptedPrivateKey = EncryptionUtils.encrypt(this.privateKey)
            return Wallet(
                userId = this.userId,
                walletAddress = this.walletAddress,
                currencyBalances = this.currencyBalances,
                privateKey = encryptedPrivateKey
            )
        }
    }
}