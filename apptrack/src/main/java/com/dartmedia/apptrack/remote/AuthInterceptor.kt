package com.dartmedia.apptrack.remote

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {

    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader(HEADER_NAME, HEADER_VALUE + it)
        }
        return chain.proceed(requestBuilder.build())
    }

    companion object {
        private const val HEADER_NAME = "Authorization" // TODO : Change Header Name & Value
        private const val HEADER_VALUE = "Bearer " // TODO : With Space ?
    }
}