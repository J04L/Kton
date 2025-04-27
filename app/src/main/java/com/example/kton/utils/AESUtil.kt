package com.example.kton.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object AESUtil {
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val IV_SIZE = 12
    private const val TAG_SIZE = 128

    fun encrypt(data: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val iv = cipher.iv
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        val ivAndEncrypted = iv + encrypted

        return Base64.encodeToString(ivAndEncrypted, Base64.DEFAULT)
    }

    fun decrypt(encryptedData: String, secretKey: SecretKey): String {
        val decoded = Base64.decode(encryptedData, Base64.DEFAULT)

        val iv = decoded.copyOfRange(0, IV_SIZE)
        val encrypted = decoded.copyOfRange(IV_SIZE, decoded.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(TAG_SIZE, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        val decrypted = cipher.doFinal(encrypted)
        return String(decrypted, Charsets.UTF_8)
    }
}