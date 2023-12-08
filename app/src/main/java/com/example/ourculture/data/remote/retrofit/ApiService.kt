package com.example.ourculture.data.remote.retrofit

import com.example.ourculture.data.remote.retrofit.response.BarangResponse
import com.example.ourculture.data.remote.retrofit.response.DetailBarangResponse
import com.example.ourculture.data.remote.retrofit.response.DetailStoryResponse
import com.example.ourculture.data.remote.retrofit.response.FileUploadResponse
import com.example.ourculture.data.remote.retrofit.response.GetWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.ListStoryItem
import com.example.ourculture.data.remote.retrofit.response.LoginResponse
import com.example.ourculture.data.remote.retrofit.response.PostWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.RegisterResponse
import com.example.ourculture.data.remote.retrofit.response.SignInGoogleResponse
import com.example.ourculture.data.remote.retrofit.response.Story
import com.example.ourculture.data.remote.retrofit.response.StoryResponse
import com.example.ourculture.data.remote.retrofit.response.UploadMarketResponse
import com.example.ourculture.data.remote.retrofit.response.WhoamiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("password_repeat") repeatPassword: String,
        @Field("email") email: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/google/login")
    suspend fun googleLogin(
        @Field("googleId") googleId: String?,
        @Field("googleToken") googleToken: String?,
        @Field("username") username: String?,
        @Field("email") email: String?,
        @Field("avatar") avatar: String?
    ): SignInGoogleResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token : String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 5
    ): StoryResponse
    @GET("market/barang")
    suspend fun getAllMarket(): BarangResponse

    @GET("market/wishlist")
    suspend fun getUserWishlist(
        @Header("Authorization") token : String,
    ): GetWishlistResponse

    @GET("user/whoami")
    suspend fun getWhoami(
        @Header("Authorization") token : String,
    ): WhoamiResponse

    @FormUrlEncoded
    @POST("market/wishlist")
    suspend fun postUserWishlist(
        @Header("Authorization") token : String,
        @Field("barangId") barangId: Int?,
    ): PostWishlistResponse



    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Header("Authorization") token : String,
        @Query("location") location: Int = 1
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStories(
        @Header("Authorization") token : String,
        @Path("id") id: String
    ): DetailStoryResponse

    @GET("market/barang/detail/{id}")
    suspend fun getDetailMarketItem(
        @Path("id") id: String
    ): DetailBarangResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Header("Authorization") token : String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): FileUploadResponse

    @Multipart
    @POST("market/barang")
    suspend fun uploadToMarket(
        @Header("Authorization") token : String,
        @Part file: MultipartBody.Part,
        @Part("harga") harga: RequestBody,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("location") location: RequestBody,
        @Part("stock") stock: RequestBody,
    ): UploadMarketResponse


}