package com.dartmedia.apptrack.remote

import com.dartmedia.apptrack.remote.responses.AddLogTrackModel
import com.dartmedia.apptrack.remote.responses.AddLogTrackResponseModel
import com.dartmedia.apptrack.remote.responses.LoginResponseModel
import com.dartmedia.apptrack.remote.responses.LoginRequestModel
import com.dartmedia.apptrack.remote.responses.actionvalidation.ActionValidationResponse
import com.dartmedia.apptrack.remote.responses.getlogtrack.GetLogTrackResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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

    @GET("log-tracker")
    fun getLogTrack(

    ): Call<GetLogTrackResponseModel?>?

    @GET("validation-log-action")
    fun getActionValidation(

    ): Call<ActionValidationResponse?>?
}