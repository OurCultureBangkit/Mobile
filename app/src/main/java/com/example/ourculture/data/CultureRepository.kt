package com.example.ourculture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.ourculture.data.pref.UserModel
import com.example.ourculture.data.pref.UserPreference
import com.example.ourculture.data.remote.retrofit.response.DetailStoryResponse
import com.example.ourculture.data.remote.retrofit.response.ErrorResponse
import com.example.ourculture.data.remote.retrofit.response.FileUploadResponse
import com.example.ourculture.data.remote.retrofit.response.ListStoryItem
import com.example.ourculture.data.remote.retrofit.response.LoginResponse
import com.example.ourculture.data.remote.retrofit.response.RegisterResponse
import com.example.ourculture.data.remote.retrofit.response.StoryResponse
import com.example.ourculture.database.CultureDatabase
import com.example.ourculture.data.remote.retrofit.ApiService
import com.example.ourculture.data.remote.retrofit.response.BarangItem
import com.example.ourculture.data.remote.retrofit.response.DetailBarangResponse
import com.example.ourculture.data.remote.retrofit.response.PostWishlistResponse
import com.example.ourculture.data.remote.retrofit.response.SignInGoogleResponse
import com.example.ourculture.data.remote.retrofit.response.UploadMarketResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class CultureRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
    private val cultureDatabase: CultureDatabase
){
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


    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout(){
        userPreference.logout()
    }


    fun getAllStories(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                CulturePagingSource(apiService, token)
            }
        ).liveData
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

    fun getAllStoriesWithLocation(token: String): LiveData<Result<StoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStoriesWithLocation("Bearer $token")
            emit(Result.Success(response))
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getDetailStories(token: String, idStory: String): LiveData<Result<DetailStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailStories("Bearer $token", idStory)
            emit(Result.Success(response))
        } catch (e: HttpException){
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

    fun uploadImage(token: String, file: MultipartBody.Part, description: RequestBody): LiveData<Result<FileUploadResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadImage("Bearer $token", file, description)
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

    companion object {
        @Volatile
        private var instance: CultureRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
            cultureDatabase: CultureDatabase
        ): CultureRepository =
            instance ?: synchronized(this) {
                instance ?: CultureRepository(userPreference, apiService, cultureDatabase)
            }.also { instance = it }
    }
}