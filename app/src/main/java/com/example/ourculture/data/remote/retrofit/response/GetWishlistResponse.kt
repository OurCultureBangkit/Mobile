package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class GetWishlistResponse(

	@field:SerializedName("barang")
	val barang: List<BarangItemWishList>,

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

data class BarangItemWishList(

	@field:SerializedName("barangId")
	val barangId: Int,

	@field:SerializedName("image")
	val image: List<String>,

	@field:SerializedName("wishListId")
	val wishListId: Int,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("stock")
	val stock: Int
)
