package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class DetailBarangResponse(

	@field:SerializedName("barang")
	val barang: Barang,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)

data class Barang(

	@field:SerializedName("images")
	val images: List<String>,

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

data class PostBy(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("username")
	val username: String
)
