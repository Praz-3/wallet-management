package com.crypto.wallet.utils

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    private const val SECRET_KEY = "CryptoKey0121024"

    fun encrypt(data: String): String {
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.toByteArray()))
    }

    fun decrypt(encryptedData: String): String {
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        return String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)))
    }
}