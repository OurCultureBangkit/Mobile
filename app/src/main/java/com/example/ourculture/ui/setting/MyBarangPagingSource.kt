package com.example.ourculture.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ourculture.data.remote.retrofit.ApiService
import com.example.ourculture.data.remote.retrofit.response.MyBarangItem

class MyBarangPagingSource(private val apiService: ApiService, private val token: String): PagingSource<Int, MyBarangItem>() {
    override fun getRefreshKey(state: PagingState<Int, MyBarangItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyBarangItem> {
        _isLoading.value = true
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMyBarang(token, position, params.loadSize)
            _isLoading.value = false
            LoadResult.Page(
                data = responseData.barang,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.barang.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception){
            _isLoading.value = false

            return LoadResult.Error(exception)
        }
    }

    companion object{
        const val INITIAL_PAGE_INDEX = 1
        private val _isLoading = MutableLiveData<Boolean>()
        val isLoading : LiveData<Boolean> = _isLoading

    }

}