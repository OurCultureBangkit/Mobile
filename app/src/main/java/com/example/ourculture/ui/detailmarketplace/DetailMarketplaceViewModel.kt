package com.example.ourculture.ui.detailmarketplace

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserModel
import com.example.ourculture.data.remote.retrofit.ApiConfig
import com.example.ourculture.data.remote.retrofit.response.CommmentsItem
import com.example.ourculture.data.remote.retrofit.response.GetBarangCommentResponse
import com.example.ourculture.data.remote.retrofit.response.PostCommentResponse
import com.example.ourculture.data.remote.retrofit.response.ReplyCommentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class DetailMarketplaceViewModel(private val cultureRepository: CultureRepository): ViewModel() {
    fun getDetailMarketItem(idItem: String) = cultureRepository.getDetailMarketItem(idItem)

    fun postUserWishlist(token: String, barangId: Int) = cultureRepository.postUserWishlist(token, barangId)

    fun getSession(): LiveData<UserModel> {
        return cultureRepository.getSession().asLiveData()
    }

//    fun getCommentMarketItem(token: String, id: String) = cultureRepository.getCommentMarketItem(token, id)
//
//    fun postReplyComment(token: String, idBarang: String, idKomen: String,comment: String) = cultureRepository.postReplyComment(token, idBarang, idKomen, comment)

    private val _listComment = MutableLiveData<List<CommmentsItem>>()
    val listComment: LiveData<List<CommmentsItem>> = _listComment

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getCommentMarketItem(token: String, id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCommentMarketItem(token, id)
        client.enqueue(object: Callback<GetBarangCommentResponse>{
            override fun onResponse(
                call: Call<GetBarangCommentResponse>,
                response: Response<GetBarangCommentResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listComment.value = response.body()?.commments
                } else {
                    Log.e("DetailMarketplaceViewModel", "isFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetBarangCommentResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailMarketplaceViewModel", "isFailure: ${t.message.toString()}")
            }

        })

    }

    fun postComment(token: String, idBarang: String, comment: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postComment(token, idBarang, comment)
        client.enqueue(object: Callback<PostCommentResponse>{
            override fun onResponse(
                call: Call<PostCommentResponse>,
                response: Response<PostCommentResponse>
            ) {
                if (response.isSuccessful) {
                    val clientDua = ApiConfig.getApiService().getCommentMarketItem(token, idBarang)
                    clientDua.enqueue(object: Callback<GetBarangCommentResponse>{
                        override fun onResponse(
                            call: Call<GetBarangCommentResponse>,
                            response: Response<GetBarangCommentResponse>
                        ) {
                            _isLoading.value = false
                            if (response.isSuccessful) {
                                _listComment.value = response.body()?.commments
                            } else {
                                Log.e("DetailMarketplaceViewModel", "isFailure: ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<GetBarangCommentResponse>, t: Throwable) {
                            _isLoading.value = false
                            Log.e("DetailMarketplaceViewModel", "isFailure: ${t.message.toString()}")
                        }

                    })
                }
            }

            override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {

            }

        })
    }

    fun postReplyComment(token: String, idBarang: String, idKomen: String,comment: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postReplyComment(token, idBarang, idKomen, comment)
        client.enqueue(object: Callback<ReplyCommentResponse>{
            override fun onResponse(
                call: Call<ReplyCommentResponse>,
                response: Response<ReplyCommentResponse>
            ) {
                if (response.isSuccessful) {
                    val clientDua = ApiConfig.getApiService().getCommentMarketItem(token, idBarang)
                    clientDua.enqueue(object: Callback<GetBarangCommentResponse>{
                        override fun onResponse(
                            call: Call<GetBarangCommentResponse>,
                            response: Response<GetBarangCommentResponse>
                        ) {
                            _isLoading.value = false
                            if (response.isSuccessful) {
                                _listComment.value = response.body()?.commments
                            } else {
                                Log.e("DetailMarketplaceViewModel", "isFailure: ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<GetBarangCommentResponse>, t: Throwable) {
                            _isLoading.value = false
                            Log.e("DetailMarketplaceViewModel", "isFailure: ${t.message.toString()}")
                        }

                    })
                } else {
                    _isLoading.value = false
                    Log.e("DetailMarketplaceViewModel", "isFailure: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<ReplyCommentResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailMarketplaceViewModel", "isFailure: ${t.message.toString()}")
            }

        })
    }
}