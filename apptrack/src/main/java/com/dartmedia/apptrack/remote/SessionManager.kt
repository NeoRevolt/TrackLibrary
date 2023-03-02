package com.dartmedia.apptrack.remote

import android.content.Context
import android.content.SharedPreferences
import android.view.Display.Mode
import com.dartmedia.apptrack.R

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
        const val APP_NAME = "tracking_app" //TODO (Change to Core module name)
    }
}