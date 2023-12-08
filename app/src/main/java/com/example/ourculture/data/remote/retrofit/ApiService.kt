package com.example.ourculture.data.remote.retrofit

import com.example.ourculture.data.remote.retrofit.response.BarangResponse
import com.example.ourculture.data.remote.retrofit.response.DeleteWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.DetailBarangResponse
import com.example.ourculture.data.remote.retrofit.response.GetBarangCommentResponse
import com.example.ourculture.data.remote.retrofit.response.GetWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.LoginResponse
import com.example.ourculture.data.remote.retrofit.response.MyBarangResponse
import com.example.ourculture.data.remote.retrofit.response.PostWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.RegisterResponse
import com.example.ourculture.data.remote.retrofit.response.SignInGoogleResponse
import com.example.ourculture.data.remote.retrofit.response.UploadMarketResponse
import com.example.ourculture.data.remote.retrofit.response.WhoamiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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

    @GET("market/barang/detail/{id}")
    suspend fun getDetailMarketItem(
        @Path("id") id: String
    ): DetailBarangResponse

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

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "market/wishlist", hasBody = true)
    suspend fun deleteUserWishlist(
        @Header("Authorization") token : String,
        @Field("wishListId") wishListId: Int,
        ): DeleteWishlistResponse

    @GET("user/market/barang")
    suspend fun getMyBarang(
        @Header("Authorization") token : String
    ): MyBarangResponse

    @GET("market/barang/{id}/comment")
    suspend fun getCommentMarketItem(
        @Header("Authorization") token : String,
        @Path("id") id: String
    ): GetBarangCommentResponse

}