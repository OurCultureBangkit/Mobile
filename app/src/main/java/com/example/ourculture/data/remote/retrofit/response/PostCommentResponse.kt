package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class PostCommentResponse(

	@field:SerializedName("data")
	val data: PostCommentData,

	@field:SerializedName("message")
	val message: String
)

data class PostCommentData(

	@field:SerializedName("barangId")
	val barangId: Int,

	@field:SerializedName("image")
	val image: Any,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("comment")
	val comment: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
