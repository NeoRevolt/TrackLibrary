package com.dartmedia.apptrack.remote

import android.content.Context
import android.content.SharedPreferences

/**
 * Auth Interceptor for build auth header.
 *
 * Auth Token fetched from [SessionManager.fetchAuthToken] using SharedPref
 *
 * @author Dzalfikri Ali Zidan
 * @version 1.0
 * @since 3 March 2023
 *
 * @param Context
 */
class SessionManager(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = preferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return preferences.getString(USER_TOKEN, null)
    }

    companion object {
        const val USER_TOKEN = "user_token"
        const val APP_NAME = "tracking_app" // (Change to Core module name)
    }
}