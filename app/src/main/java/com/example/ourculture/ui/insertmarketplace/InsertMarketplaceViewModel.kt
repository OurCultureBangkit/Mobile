package com.example.ourculture.ui.insertmarketplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

class InsertMarketplaceViewModel(private val cultureRepository: CultureRepository): ViewModel() {
    fun uploadImage(token: String, file: MultipartBody.Part, description: RequestBody) = cultureRepository.uploadImage(token, file, description)

    fun uploadToMarket(token: String, file: MultipartBody.Part, harga: RequestBody,title: RequestBody,description: RequestBody,location: RequestBody,stock: RequestBody) = cultureRepository.uploadToMarket(token, file, harga, title, description, location, stock)

    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }
}