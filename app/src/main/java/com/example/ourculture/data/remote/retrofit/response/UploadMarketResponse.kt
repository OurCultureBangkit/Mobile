package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class UploadMarketResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("image")
	val image: List<String>,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("stock")
	val stock: Int,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
