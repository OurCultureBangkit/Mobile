package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class GetAllCultureResponse(

	@field:SerializedName("totalItems")
	val totalItems: Int,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("currentPage")
	val currentPage: Int
)

data class DataItem(

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
	val source: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
