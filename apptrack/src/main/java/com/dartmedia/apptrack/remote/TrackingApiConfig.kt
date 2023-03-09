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
        private var baseUrl: String = ""

        fun getApiService(context: Context): TrackingService {
            val apiManager = ApiManager(context)
            val newBaseUrl = apiManager.fetchBaseUrl()
            baseUrl = newBaseUrl ?: Const.DEFAULT_BASE_URL // If newBaseUrl == null then Default Base URL

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
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(TrackingService::class.java)
        }
    }
}