package com.example.ourculture.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ourculture.data.remote.retrofit.ApiService
import com.example.ourculture.data.remote.retrofit.response.DataItem
import com.example.ourculture.ui.marketplace.MarketplacePagingSource

class CulturePagingSource(private val apiService: ApiService): PagingSource<Int, DataItem>() {
    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        _isLoading.value = true
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllCulture(position, params.loadSize)
            _isLoading.value = false
            LoadResult.Page(
                data = responseData.data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.data.isNullOrEmpty()) null else position + 1
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