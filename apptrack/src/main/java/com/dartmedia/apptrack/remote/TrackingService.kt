package com.dartmedia.apptrack.remote

import com.dartmedia.apptrack.remote.responses.AddLogTrackModel
import com.dartmedia.apptrack.remote.responses.AddLogTrackResponseModel
import com.dartmedia.apptrack.remote.responses.LoginResponseModel
import com.dartmedia.apptrack.remote.responses.LoginRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TrackingService {

    @POST("authentication")
    fun login(
        @Body loginRequestModel: LoginRequestModel
    ): Call<LoginResponseModel?>?

    @POST("log-tracker")
    fun addLogTrack(
        @Body addLogTrackModel: AddLogTrackModel
    ): Call<AddLogTrackResponseModel?>?
}