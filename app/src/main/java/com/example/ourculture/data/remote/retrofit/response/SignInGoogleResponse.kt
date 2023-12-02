package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class SignInGoogleResponse(

	@field:SerializedName("user")
	val userGoogle: UserGoogle,

	@field:SerializedName("token")
	val token: String
)

data class UserGoogle(

	@field:SerializedName("googleId")
	val googleId: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("googleToken")
	val googleToken: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
