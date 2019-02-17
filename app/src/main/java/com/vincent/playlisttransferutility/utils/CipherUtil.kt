package com.vincent.playlisttransferutility.utils

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.String.format
import java.math.BigInteger
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException

class CipherUtil {

    fun createGooglePlayMusicAuthSignature(username: String,
                                           password: String,
                                           modulus: BigInteger,
                                           exponent: BigInteger): ByteArray {
        try {
            ByteArrayOutputStream().use { bytes ->
                bytes.write(0)
                bytes.write(Arrays.copyOfRange(sha1(createKeyStruct(modulus, exponent)), 0, 4))
                bytes.write(pkcs1AoepEncode(format("%s\u0000%s", username, password).toByteArray(), createKey(modulus, exponent)))
                return bytes.toByteArray()
            }
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }

    }

    private fun pkcs1AoepEncode(bytes: ByteArray,
                                publicKey: PublicKey): ByteArray {
        try {
            val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            return cipher.doFinal(bytes)
        } catch (e: InvalidKeyException) {
            throw IllegalStateException(e)
        } catch (e: BadPaddingException) {
            throw IllegalStateException(e)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException(e)
        } catch (e: NoSuchPaddingException) {
            throw IllegalStateException(e)
        } catch (e: IllegalBlockSizeException) {
            throw IllegalStateException(e)
        }

    }

    private fun createKey(modulus: BigInteger,
                          exponent: BigInteger): PublicKey {
        try {
            val factory = KeyFactory.getInstance("RSA")
            val keySpec = RSAPublicKeySpec(modulus, exponent)
            return factory.generatePublic(keySpec)
        } catch (e: InvalidKeySpecException) {
            throw IllegalStateException(e)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException(e)
        }

    }

    private fun sha1(bytes: ByteArray): ByteArray {
        try {
            return MessageDigest.getInstance("SHA1").digest(bytes)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException(e)
        }

    }

    private fun createKeyStruct(modulus: BigInteger,
                                exponent: BigInteger): ByteArray {
        try {
            ByteArrayOutputStream().use { bytes ->
                bytes.write(byteArrayOf(0x00, 0x00, 0x00, 0x80.toByte()))
                bytes.write(bigIntegerToBytesWithoutSign(modulus))
                bytes.write(byteArrayOf(0x00, 0x00, 0x00, 0x03))
                bytes.write(bigIntegerToBytesWithoutSign(exponent))
                return bytes.toByteArray()
            }
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }

    }

    private fun bigIntegerToBytesWithoutSign(bigInteger: BigInteger): ByteArray {
        val bytes = bigInteger.toByteArray()
        return if (bytes[0].toInt() == 0) Arrays.copyOfRange(bytes, 1, bytes.size) else bytes
    }
}