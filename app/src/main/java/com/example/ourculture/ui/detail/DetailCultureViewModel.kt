package com.example.ourculture.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel
import kotlinx.coroutines.launch

class DetailCultureViewModel(private val cultureRepository: CultureRepository): ViewModel() {

    fun getDetailCulture(idCulture: Int) = cultureRepository.getDetailCulture(idCulture)


    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            cultureRepository.logout()
        }
    }

}