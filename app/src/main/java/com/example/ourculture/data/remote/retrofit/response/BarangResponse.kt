package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class BarangResponse(

	@field:SerializedName("barang")
	val barang: List<BarangItem>,

	@field:SerializedName("totalItems")
	val totalItems: Int,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("currentPage")
	val currentPage: Int
)

data class BarangItem(

	@field:SerializedName("images")
	val images: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("postBy")
	val postBy: PostBy,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("stock")
	val stock: Int
)

data class barangPostBy(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("username")
	val username: String
)
