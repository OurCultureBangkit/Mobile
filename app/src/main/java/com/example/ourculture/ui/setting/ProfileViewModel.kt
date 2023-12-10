package com.example.ourculture.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel
import com.example.ourculture.data.remote.retrofit.response.BarangItem
import com.example.ourculture.data.remote.retrofit.response.MyBarangItem

class ProfileViewModel(private val cultureRepository: CultureRepository): ViewModel() {
    fun getWhoami(token: String) = cultureRepository.getWhoami(token)

    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }

    fun getMyBarang(token: String): LiveData<PagingData<MyBarangItem>> = cultureRepository.getMyBarang(token).cachedIn(viewModelScope)

}