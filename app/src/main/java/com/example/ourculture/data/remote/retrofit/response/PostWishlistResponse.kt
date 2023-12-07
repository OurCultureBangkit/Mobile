package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class PostWishlistResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: PostWishlistData,

	@field:SerializedName("message")
	val message: String
)

data class PostWishlistData(

	@field:SerializedName("barangId")
	val barangId: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
