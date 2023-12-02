package com.example.ourculture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel
import com.example.ourculture.data.remote.retrofit.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val cultureRepository: CultureRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            cultureRepository.logout()
        }
    }

}