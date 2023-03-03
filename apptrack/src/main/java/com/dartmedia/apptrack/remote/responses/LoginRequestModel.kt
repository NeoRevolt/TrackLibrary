package com.dartmedia.apptrack.remote.responses

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("email")
	val email: String,

    @field:SerializedName("fcmToken")
    val fcmToken: String

)
