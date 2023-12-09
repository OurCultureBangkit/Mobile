package com.example.ourculture.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel
import com.example.ourculture.database.WishlistEntity
import kotlinx.coroutines.launch

class WishlistViewModel(private val cultureRepository: CultureRepository) : ViewModel() {
    fun getUserWishlist(token: String) = cultureRepository.getUserWishlist(token)

    fun getWishlist(token: String) = cultureRepository.getWishlist(token)

    fun deleteWishItem(wishList: WishlistEntity) {
        viewModelScope.launch {
            cultureRepository.deleteWishItem(wishList)
        }
    }


    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }

    fun deleteUserWishlist(token: String, wishListId: Int) = cultureRepository.deleteUserWishlist(token, wishListId)
}