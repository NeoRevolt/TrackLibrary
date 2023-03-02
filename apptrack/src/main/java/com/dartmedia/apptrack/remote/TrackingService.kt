package com.dartmedia.apptrack.remote

import androidx.annotation.RawRes
import com.dartmedia.apptrack.remote.responses.LoginModel
import com.dartmedia.apptrack.remote.responses.LoginRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface TrackingService {

    @POST("authentication")
    fun login(
        @Body loginRequestModel: LoginRequestModel
    ): Call<LoginModel?>?
}