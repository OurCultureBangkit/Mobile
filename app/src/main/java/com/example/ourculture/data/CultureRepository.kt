package com.example.ourculture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.ourculture.data.pref.UserModel
import com.example.ourculture.data.pref.UserPreference
import com.example.ourculture.data.remote.retrofit.response.ErrorResponse
import com.example.ourculture.data.remote.retrofit.response.LoginResponse
import com.example.ourculture.data.remote.retrofit.response.RegisterResponse
import com.example.ourculture.data.remote.retrofit.ApiService
import com.example.ourculture.data.remote.retrofit.response.BarangItem
import com.example.ourculture.data.remote.retrofit.response.BarangItemWishList
import com.example.ourculture.data.remote.retrofit.response.CommmentsItem
import com.example.ourculture.data.remote.retrofit.response.DeleteWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.DetailBarangResponse
import com.example.ourculture.data.remote.retrofit.response.MyBarangItem
import com.example.ourculture.data.remote.retrofit.response.PostWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.ProfileWhoami
import com.example.ourculture.data.remote.retrofit.response.SignInGoogleResponse
import com.example.ourculture.data.remote.retrofit.response.UploadMarketResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class CultureRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
){
    fun getCommentMarketItem(token: String, id: String): LiveData<Result<List<CommmentsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getCommentMarketItem(token, id)
            emit(Result.Success(response.commments))
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getMyBarang(token: String): LiveData<Result<List<MyBarangItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getMyBarang(token)
            emit(Result.Success(response.barang))
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getAllMarket(): LiveData<Result<List<BarangItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllMarket()
            emit(Result.Success(response.barang))
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun postUserLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try{
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun postUserLoginGoogle(googleId: String?, googleToken: String?, username: String?, email: String?, avatar: String?): LiveData<Result<SignInGoogleResponse>> = liveData {
        emit(Result.Loading)
        try{
            val response = apiService.googleLogin(googleId, googleToken, username, email, avatar)
            emit(Result.Success(response))
        } catch (e: Exception){
            emit(Result.Error(e.toString()))
        }
    }

     fun postUserRegister(username: String, password: String, repeatPassword: String, email: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try{
            val response = apiService.register(username, password, repeatPassword, email)
            emit(Result.Success(response))
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getUserWishlist(token: String): LiveData<Result<List<BarangItemWishList>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserWishlist(token)
            emit(Result.Success(response.barang))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getWhoami(token: String): LiveData<Result<ProfileWhoami>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getWhoami(token)
            emit(Result.Success(response.profile))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun deleteUserWishlist(token: String, wishListId: Int): LiveData<Result<DeleteWishlistResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.deleteUserWishlist(token, wishListId)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getDetailMarketItem(idStory: String): LiveData<Result<DetailBarangResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailMarketItem(idStory)
            emit(Result.Success(response))
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun uploadToMarket(token: String, file: MultipartBody.Part, harga: RequestBody,title: RequestBody,description: RequestBody,location: RequestBody,stock: RequestBody): LiveData<Result<UploadMarketResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadToMarket(token, file, harga, title, description, location, stock)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun postUserWishlist(token: String, barangId: Int): LiveData<Result<PostWishlistResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postUserWishlist(token, barangId)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout(){
        userPreference.logout()
    }


    companion object {
        @Volatile
        private var instance: CultureRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ): CultureRepository =
            instance ?: synchronized(this) {
                instance ?: CultureRepository(userPreference, apiService)
            }.also { instance = it }
    }
}