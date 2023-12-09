package com.example.ourculture.di

import android.content.Context
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.data.pref.UserPreference
import com.example.ourculture.data.pref.dataStore
import com.example.ourculture.data.remote.retrofit.ApiConfig
import com.example.ourculture.database.WishlistDatabase

object Injection {
    fun provideRepository(context: Context): CultureRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val database = WishlistDatabase.getInstance(context)
        val dao = database.wishlistDao()
        return CultureRepository.getInstance(pref, apiService, dao)
    }
}
