package com.example.ourculture.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class GetBarangCommentResponse(

	@field:SerializedName("totalItems")
	val totalItems: Int,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("commments")
	val commments: List<CommmentsItem>,

	@field:SerializedName("currentPage")
	val currentPage: Int
)

data class CommentPostBy(

	@field:SerializedName("avatar")
	val avatar: String?,

	@field:SerializedName("username")
	val username: String
)

data class RepliesItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("images")
	val images: String?,

	@field:SerializedName("postBy")
	val postBy: CommentPostBy,

	@field:SerializedName("comment")
	val comment: String,

	@field:SerializedName("id")
	val id: Int
)

data class CommmentsItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("images")
	val images: String?,

	@field:SerializedName("replies")
	val replies: List<RepliesItem>,

	@field:SerializedName("postBy")
	val postBy: CommentPostBy,

	@field:SerializedName("comment")
	val comment: String,

	@field:SerializedName("id")
	val id: Int
)
