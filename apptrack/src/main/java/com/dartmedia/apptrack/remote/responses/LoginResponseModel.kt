package com.dartmedia.apptrack.remote.responses

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("accessToken")
	val accessToken: String
)
