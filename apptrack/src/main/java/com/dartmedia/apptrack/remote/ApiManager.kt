package com.dartmedia.apptrack.remote

import android.content.Context
import android.content.SharedPreferences

class ApiManager(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)

    fun setBaseUrl(url: String) {
        val editor = preferences.edit()
        editor.putString(USER_BASE_URL, url)
        editor.apply()
    }

    fun fetchBaseUrl(): String? {
        return preferences.getString(USER_BASE_URL, null)
    }

    companion object {
        const val USER_BASE_URL = "user_base_url"
        const val APP_NAME = "tracking_app"
    }
}