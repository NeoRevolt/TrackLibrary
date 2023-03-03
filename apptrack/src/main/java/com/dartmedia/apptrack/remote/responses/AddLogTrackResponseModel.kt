package com.dartmedia.apptrack.remote.responses

import com.google.gson.annotations.SerializedName

data class AddLogTrackResponseModel(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
