package com.example.ourculture.data.remote.retrofit

import com.example.ourculture.data.remote.retrofit.response.BarangResponse
import com.example.ourculture.data.remote.retrofit.response.DeleteWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.DetailBarangResponse
import com.example.ourculture.data.remote.retrofit.response.GetAllCultureResponse
import com.example.ourculture.data.remote.retrofit.response.GetBarangCommentResponse
import com.example.ourculture.data.remote.retrofit.response.GetDetailCultureResponse
import com.example.ourculture.data.remote.retrofit.response.GetWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.LoginResponse
import com.example.ourculture.data.remote.retrofit.response.MyBarangResponse
import com.example.ourculture.data.remote.retrofit.response.PostCommentResponse
import com.example.ourculture.data.remote.retrofit.response.PostImageDetectionResponse
import com.example.ourculture.data.remote.retrofit.response.PostWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.RegisterResponse
import com.example.ourculture.data.remote.retrofit.response.ReplyCommentResponse
import com.example.ourculture.data.remote.retrofit.response.SignInGoogleResponse
import com.example.ourculture.data.remote.retrofit.response.UploadMarketResponse
import com.example.ourculture.data.remote.retrofit.response.WhoamiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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


    @GET("user/market/barang")
    suspend fun getMyBarang(
        @Header("Authorization") token : String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 5
    ): MyBarangResponse


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

    @GET("culture/{id_culture}")
    suspend fun getDetailCulture(
        @Path("id_culture") idCulture: Int
    ): GetDetailCultureResponse


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

    @Multipart
    @POST("ml/vision")
    suspend fun uploadToDetection(
        @Part file: MultipartBody.Part,
    ): PostImageDetectionResponse

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "market/wishlist", hasBody = true)
    suspend fun deleteUserWishlist(
        @Header("Authorization") token : String,
        @Field("wishListId") wishListId: Int,
        ): DeleteWishlistResponse

    @GET("market/barang/{id}/comment")
    suspend fun getCommentMarketItem(
        @Path("id") id: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 5
    ): GetBarangCommentResponse

    @GET("culture")
    suspend fun getAllCulture(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 5
    ): GetAllCultureResponse

    @GET("market/barang")
    suspend fun getAllMarket(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 5
    ): BarangResponse

    @FormUrlEncoded
    @POST("market/barang/{id_barang}/comment/{id_komen}/replies")
     suspend fun postReplyComment(
        @Header("Authorization") token : String,
        @Path("id_barang") idBarang: String,
        @Path("id_komen") idKomen: String,
        @Field("comment") comment: String,
        @Field("rating") rating: Float = 2.0F,
    ): ReplyCommentResponse

    @FormUrlEncoded
    @POST("market/barang/{id_barang}/comment")
     suspend fun postComment(
        @Header("Authorization") token : String,
        @Path("id_barang") idBarang: String,
        @Field("comment") comment: String,
        @Field("rating") rating: Float = 2.0F,
    ): PostCommentResponse

}