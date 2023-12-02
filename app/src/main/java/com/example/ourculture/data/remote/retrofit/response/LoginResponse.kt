package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("user")
	val userLogin: UserLogin,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("token")
	val token: String
)

data class UserLogin(

	@field:SerializedName("googleId")
	val googleId: Any,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar")
	val avatar: Any,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("googleToken")
	val googleToken: Any,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
