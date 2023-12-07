package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("access_token")
	val accessToken: String,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("profile")
	val profile: ProfileLogin,

	@field:SerializedName("status")
	val status: String
)

data class ProfileLogin(

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar")
	val avatar: Any,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
