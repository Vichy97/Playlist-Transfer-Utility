package com.vincent.playlisttransferutility.ui.googlelogin

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import android.util.Base64
import android.util.Base64.URL_SAFE
import android.util.Log
import androidx.core.content.ContextCompat
import com.vincent.playlisttransferutility.network.googleplaymusic.GooglePlayMusicApi
import com.vincent.playlisttransferutility.utils.CipherUtil
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import java.math.BigInteger

class GoogleLoginModel(private val context: Context,
                       private val googlePlayMusicApi: GooglePlayMusicApi,
                       private val telephonyManager: TelephonyManager,
                       private val cipherUtil: CipherUtil) {

    companion object {
        private const val AUTH_URL = "https://android.clients.google.com/auth"
        private const val SERVICE = "ac2dm"
        private const val APP = "com.google.android.music"
        private const val CLIENT_SIGNATURE = "AIzaSyARTC1h-_puqO0PHCHUoj1BTDjuAOxNVA8"
        private const val DEVICE_COUNTRY = "us"
        private const val OPERATOR_COUNTRY = "us"
        private const val LANG = "en"
        private const val SDK_VERSION = 17
        private const val ACCOUNT_TYPE = "HOSTED_OR_GOOGLE"
        private const val SOURCE = "android"
        private val MODULUS: BigInteger = BigInteger.ONE //TODO: fetch from file (way too large of a number)
        private val EXPONENT: BigInteger = BigInteger.TEN //TODO: fetch from file
    }

    fun loginToGooglePlay(email: String, password: String): Completable {
        if (hasReadPhoneStatePermission()) {
            return Completable.fromSingle(performLoginCall(email, password))
        }

        return Completable.error(Exception("Access to EMEI required for authentication"))
    }

    private fun hasReadPhoneStatePermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    private fun performLoginCall(email: String, password: String): Single<Response<ResponseBody>> {
        val emei = telephonyManager.deviceId
        val signature = getEncodedSignature(email, password)

        return googlePlayMusicApi.authenticate(
                AUTH_URL,
                signature,
                DEVICE_COUNTRY,
                ACCOUNT_TYPE,
                emei,
                email,
                LANG,
                1,
                SERVICE,
                OPERATOR_COUNTRY,
                SOURCE,
                SDK_VERSION,
                1
        ).doOnSuccess {
            Log.d("TAG", it.toString())
        }
    }

    private fun getEncodedSignature(email: String, password: String): String {
        val signature = cipherUtil.createGooglePlayMusicAuthSignature(email, password, MODULUS, EXPONENT)
        return Base64.encodeToString(signature, URL_SAFE)
    }
}