package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class DeleteWishlistResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)
