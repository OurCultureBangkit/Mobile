package com.example.ourculture.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel
import kotlinx.coroutines.launch

class SignUpViewModel(private val cultureRepository: CultureRepository): ViewModel() {
    fun postUserRegister(username: String, password: String, repeatPassword: String, email: String) = cultureRepository.postUserRegister(username, password, repeatPassword, email)


}