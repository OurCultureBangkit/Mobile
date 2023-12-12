package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class PostImageDetectionResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: DetectionData,

	@field:SerializedName("postBy")
	val postBy: CulturePostBy,

	@field:SerializedName("message")
	val message: String
)

data class CulturePostBy(

	@field:SerializedName("username")
	val username: String
)

data class DetectionData(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("source")
	val source: Any
)
