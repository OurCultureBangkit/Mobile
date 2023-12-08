package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class WhoamiResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("profile")
	val profile: ProfileWhoami,

	@field:SerializedName("message")
	val message: String
)

data class ProfileWhoami(

	@field:SerializedName("googleId")
	val googleId: String,

	@field:SerializedName("roles")
	val roles: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar")
	val avatar: String?,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
