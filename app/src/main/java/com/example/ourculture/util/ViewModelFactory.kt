package com.example.ourculture.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ourculture.data.CultureRepository
import com.example.ourculture.di.Injection
import com.example.ourculture.ui.detail.DetailCultureViewModel
import com.example.ourculture.ui.detailmarketplace.DetailMarketplaceViewModel
import com.example.ourculture.ui.home.HomeViewModel
import com.example.ourculture.ui.insertmarketplace.InsertMarketplaceViewModel
import com.example.ourculture.ui.login.LoginViewModel
import com.example.ourculture.ui.main.MainViewModel
import com.example.ourculture.ui.marketplace.MarketplaceViewModel
import com.example.ourculture.ui.signup.SignUpViewModel

class ViewModelFactory private constructor(private val repository: CultureRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailCultureViewModel::class.java) -> {
                DetailCultureViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MarketplaceViewModel::class.java) -> {
                MarketplaceViewModel(repository) as T
            }
            modelClass.isAssignableFrom(InsertMarketplaceViewModel::class.java) -> {
                InsertMarketplaceViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailMarketplaceViewModel::class.java) -> {
                DetailMarketplaceViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }

    }
}