package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class ReplyCommentResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: ReplyData,

	@field:SerializedName("message")
	val message: String
)

data class ReplyData(

	@field:SerializedName("barangId")
	val barangId: Int,

	@field:SerializedName("image")
	val image: Any,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("commentId")
	val commentId: Int,

	@field:SerializedName("comment")
	val comment: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
