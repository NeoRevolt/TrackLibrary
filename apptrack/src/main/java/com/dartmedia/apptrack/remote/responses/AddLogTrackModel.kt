package com.dartmedia.apptrack.remote.responses

import com.google.gson.annotations.SerializedName

data class AddLogTrackModel(

	@field:SerializedName("nameAction")
	val nameAction: String,

	@field:SerializedName("action")
	val action: String
)
