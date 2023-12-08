package com.example.ourculture.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel

class ProfileViewModel(private val cultureRepository: CultureRepository): ViewModel() {
    fun getWhoami(token: String) = cultureRepository.getWhoami(token)

    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }

}