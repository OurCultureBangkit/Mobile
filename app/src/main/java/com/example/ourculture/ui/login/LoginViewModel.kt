package com.example.ourculture.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val cultureRepository: CultureRepository): ViewModel() {
    fun postUserLogin(email: String, password: String) = cultureRepository.postUserLogin(email, password)
    fun postUserLoginGoogle(googleId: String?, googleToken: String?, username: String?, email: String?, avatar: String?) = cultureRepository.postUserLoginGoogle(googleId, googleToken, username, email, avatar)
    fun saveSession(user: UserModel){
        viewModelScope.launch {
            cultureRepository.saveSession(user)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }


}