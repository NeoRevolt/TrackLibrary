package com.dartmedia.apptrack.remote

import android.content.Context
import android.content.SharedPreferences
import com.dartmedia.apptrack.utills.Const

class ApiManager(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)

    fun setBaseUrl(ip: String) {
        val editor = preferences.edit()
        if (ip.isNotBlank() && ip.isNotEmpty()) {
            editor.putString(USER_BASE_URL,"http://$ip:5000/api/")
        }else{
            editor.putString(USER_BASE_URL, Const.DEFAULT_BASE_URL)
        }

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