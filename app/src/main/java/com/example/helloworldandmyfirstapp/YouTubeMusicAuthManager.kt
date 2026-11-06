package com.example.helloworldandmyfirstapp

import android.content.Context
import android.webkit.CookieManager
import java.security.MessageDigest

object YouTubeMusicAuthManager {
    private const val PREFS_NAME = "yt_music_auth_prefs"
    private const val KEY_COOKIES = "yt_cookies"
    private const val KEY_USER_NAME = "yt_user_name"

    fun saveCookies(context: Context, cookies: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_COOKIES, cookies)
            .apply()
    }

    fun getCookies(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_COOKIES, "") ?: ""
    }

    fun isLoggedIn(context: Context): Boolean {
        val cookies = getCookies(context)
        return cookies.contains("SAPISID") || cookies.contains("HSID") || cookies.contains("SSID") || cookies.contains("LOGIN_INFO")
    }

    fun saveUserName(context: Context, name: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_USER_NAME, name)
            .apply()
    }

    fun getUserName(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_USER_NAME, "YouTube Music User") ?: "YouTube Music User"
    }

    fun logout(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
        try {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
        } catch (_: Exception) {}
    }

    fun getSapisidHash(cookies: String, origin: String = "https://music.youtube.com"): String {
        val sapisid = cookies.split(";")
            .map { it.trim() }
            .firstOrNull { it.startsWith("SAPISID=") || it.startsWith("__Secure-3PAPISID=") }
            ?.substringAfter("=") ?: return ""

        val timestamp = System.currentTimeMillis() / 1000
        val payload = "$timestamp $sapisid $origin"
        val md = MessageDigest.getInstance("SHA-1")
        val digest = md.digest(payload.toByteArray(Charsets.UTF_8))
        val hex = digest.joinToString("") { "%02x".format(it) }
        return "${timestamp}_$hex"
    }
}
