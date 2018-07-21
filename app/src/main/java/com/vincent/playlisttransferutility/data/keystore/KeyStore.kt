package com.vincent.playlisttransferutility.data.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class KeyStore {

    companion object {
        private const val KEYSTORE_NAME: String = "AndroidKeyStore"
        private const val ENCRYPTION_TRANSFORMATION: String = "AES/GCM/NoPadding"
    }

    private val utf8Charset: Charset = Charset.forName("UTF-8")

    //returns byte array with initialization vector and message combined
    fun encrypt(plainText: String, keystoreAlias: String): ByteArray {
        val cipher: Cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION)
        val keyPair: KeyPair = generateKeyPair(keystoreAlias)

        cipher.init(Cipher.ENCRYPT_MODE, keyPair.private)
        val initializationVector: ByteArray = cipher.iv
        val encryptedMessage: ByteArray = cipher.doFinal(plainText.toByteArray(utf8Charset))

        return concatMessageAndIv(encryptedMessage, initializationVector)
    }

    fun decrypt(encryptedMessage: ByteArray, keyStoreAlias: String): String {
        //split the encryptedMessage into the initialization vector and the actual data
        val byteBuffer = ByteBuffer.wrap(encryptedMessage)
        val initializationVectorLength = byteBuffer.int
        val initializationVector = ByteArray(initializationVectorLength)
        byteBuffer.get(initializationVector)
        val encryptedData = ByteArray(byteBuffer.remaining())
        byteBuffer.get(encryptedData)

        //get secret key from keystore
        val keyStore: KeyStore = KeyStore.getInstance(KEYSTORE_NAME)
        keyStore.load(null)
        val secretKeyEntry: KeyStore.SecretKeyEntry = keyStore.getEntry(keyStoreAlias, null) as KeyStore.SecretKeyEntry
        val secretKey: SecretKey = secretKeyEntry.secretKey

        //initialize cipher with secret key and initialization vector
        val cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION)
        val spec = GCMParameterSpec(128, initializationVector)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        //decrypt
        val decodedData: ByteArray = cipher.doFinal(encryptedData)
        return getStringFromByteArray(decodedData)
    }

    private fun generateKeyPair(alias: String): KeyPair {
        val keyPairGenerator: KeyPairGenerator = KeyPairGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_EC, KEYSTORE_NAME)

        val parameterSpec: KeyGenParameterSpec = KeyGenParameterSpec
                .Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .build()

        keyPairGenerator.initialize(parameterSpec)
        return keyPairGenerator.genKeyPair()
    }

    //prepends the message with the initialization vector and an int indicating the iv length
    private fun concatMessageAndIv(message: ByteArray, iv: ByteArray): ByteArray {
        val byteBuffer: ByteBuffer = ByteBuffer.allocate(4 + iv.size + message.size)
        byteBuffer.putInt(iv.size)
        byteBuffer.put(iv)
        byteBuffer.put(message)
        return byteBuffer.array()
    }

    private fun getStringFromByteArray(data: ByteArray): String {
        return String(data, utf8Charset)
    }
}