package com.vincent.playlisttransferutility.ui.googlelogin

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import io.reactivex.Completable
import svarzee.gps.gpsoauth.Gpsoauth

class GoogleLoginModel(private val context: Context,
                       private val gpsoauth: Gpsoauth,
                       private val telephonyManager: TelephonyManager) {

    @SuppressLint("HardwareIds")
    fun loginToGooglePlay(email: String, password: String): Completable {
        return Completable.create {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) !=
                    PackageManager.PERMISSION_GRANTED) {
                return@create it.onError(Exception("Access to EMEI required for authentication"))
            }

            val emei: String = telephonyManager.deviceId
            val authToken: svarzee.gps.gpsoauth.AuthToken = gpsoauth.login(email, password, emei, "sj",
                    "com.google.android.music", "AIzaSyARTC1h-_puqO0PHCHUoj1BTDjuAOxNVA8")
        }
    }
}