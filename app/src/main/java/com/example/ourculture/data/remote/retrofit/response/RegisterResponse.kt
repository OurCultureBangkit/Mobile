package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("profile")
	val profile: ProfileRegister,

	@field:SerializedName("status")
	val status: String
)

data class ProfileRegister(

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
