package com.example.ourculture.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel
import com.example.ourculture.data.remote.retrofit.response.ListStoryItem

class HomeViewModel(private val cultureRepository: CultureRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getAllCulture(token: String): LiveData<PagingData<ListStoryItem>> = cultureRepository.getAllStories(token).cachedIn(viewModelScope)

}