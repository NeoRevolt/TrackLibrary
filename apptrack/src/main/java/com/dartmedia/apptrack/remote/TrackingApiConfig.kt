package com.dartmedia.apptrack.remote

import android.content.Context
import com.dartmedia.apptrack.BuildConfig
import com.dartmedia.apptrack.utills.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrackingApiConfig {

    companion object {

        fun getApiService(context: Context): TrackingService {
            val apiManager = ApiManager(context)
            val newBaseUrl = apiManager.fetchBaseUrl()
            // If newBaseUrl == null then Default Base URL

            val loggingInterceptor =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Const.DEFAULT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(TrackingService::class.java)
        }
    }
}