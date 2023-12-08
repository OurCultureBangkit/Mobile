package com.example.ourculture.ui.detailmarketplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel

class DetailMarketplaceViewModel(private val cultureRepository: CultureRepository): ViewModel() {
    fun getDetailMarketItem(idItem: String) = cultureRepository.getDetailMarketItem(idItem)

    fun postUserWishlist(token: String, barangId: Int) = cultureRepository.postUserWishlist(token, barangId)

    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }

    fun getCommentMarketItem(token: String, id: String) = cultureRepository.getCommentMarketItem(token, id)
}