package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class MyBarangResponse(

	@field:SerializedName("barang")
	val barang: List<MyBarangItem>,

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

data class MyPostBy(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("username")
	val username: String
)

data class MyBarangItem(

	@field:SerializedName("barangId")
	val barangId: Int,

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

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("stock")
	val stock: Int
)
