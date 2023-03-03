package com.dartmedia.apptrack.remote.responses.actionvalidation

import com.google.gson.annotations.SerializedName

data class ActionValidationResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("validations")
	val validations: List<ValidationsItem>
)

data class ValidationsItem(

	@field:SerializedName("nameAction")
	val nameAction: String,

	@field:SerializedName("action")
	val action: String,

	@field:SerializedName("id_validation")
	val idValidation: String
)
