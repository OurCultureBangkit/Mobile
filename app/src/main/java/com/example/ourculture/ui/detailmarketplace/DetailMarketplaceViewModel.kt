package com.example.ourculture.ui.detailmarketplace

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel
import com.example.ourculture.data.remote.retrofit.response.CommmentsItem
import kotlinx.coroutines.launch

class DetailMarketplaceViewModel(private val cultureRepository: CultureRepository): ViewModel() {

    private val _listComment = MutableLiveData<PagingData<CommmentsItem>>()
    val listComment: LiveData<PagingData<CommmentsItem>> = _listComment

    fun deleteMyBarang(token: String, idBarang: String) = cultureRepository.deleteMyBarang(token, idBarang)

    fun getDetailMarketItem(idItem: String) = cultureRepository.getDetailMarketItem(idItem)

    fun postUserWishlist(token: String, barangId: Int) = cultureRepository.postUserWishlist(token, barangId)

    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }
    fun getCommentMarketItem(lifecycle: LifecycleOwner, id: String){
        cultureRepository.getCommentMarketItem(id).observe(lifecycle) {
            _listComment.value = it
        }
    }

    fun postComment(lifecycle: LifecycleOwner, token: String, idBarang: String, comment: String) {
        viewModelScope.launch {
            cultureRepository.postComment(token, idBarang, comment)
            cultureRepository.getCommentMarketItem(idBarang).observe(lifecycle) {
                _listComment.value = it
            }
        }
    }

    fun postReplyComment(lifecycle: LifecycleOwner, token: String, idBarang: String, idKomen: String, comment: String) {
        viewModelScope.launch {
            cultureRepository.postReplyComment(token, idBarang, idKomen, comment)
            cultureRepository.getCommentMarketItem(idBarang).observe(lifecycle) {
                _listComment.value = it
            }
        }
    }

}