package com.example.ourculture.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel

class WishlistViewModel(private val cultureRepository: CultureRepository) : ViewModel() {
    fun getUserWishlist(token: String) = cultureRepository.getUserWishlist(token)

    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }

}