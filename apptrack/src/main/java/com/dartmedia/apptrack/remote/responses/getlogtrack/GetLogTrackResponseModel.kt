package com.dartmedia.apptrack.remote.responses.getlogtrack

import com.google.gson.annotations.SerializedName

data class GetLogTrackResponseModel(

	@field:SerializedName("totalData")
	val totalData: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("logTrackers")
	val logTrackers: List<LogTrackersItem>
)

data class LogTrackersItem(

	@field:SerializedName("idUser")
	val idUser: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("nameUser")
	val nameUser: String,

	@field:SerializedName("emailUser")
	val emailUser: String,

	@field:SerializedName("nameAction")
	val nameAction: String,

	@field:SerializedName("action")
	val action: String,

	@field:SerializedName("idLog")
	val idLog: Int
)
