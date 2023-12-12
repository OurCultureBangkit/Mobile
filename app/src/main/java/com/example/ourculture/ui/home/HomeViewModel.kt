package com.example.ourculture.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.remote.retrofit.response.DataItem

class HomeViewModel(private val cultureRepository: CultureRepository) : ViewModel() {

    val culture: LiveData<PagingData<DataItem>> = cultureRepository.getAllCulture().cachedIn(viewModelScope)


}