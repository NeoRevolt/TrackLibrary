package com.dartmedia.apptrack.remote

import android.content.Context
import com.dartmedia.apptrack.utills.Const
import okhttp3.Interceptor
import okhttp3.Response

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
class AuthInterceptor(context: Context) : Interceptor {

    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader(Const.HEADER_NAME, Const.HEADER_VALUE + it)
        }
        return chain.proceed(requestBuilder.build())
    }

}