package com.example.ourculture.ui.detailmarketplace

import androidx.lifecycle.ViewModel
import com.example.ourculture.data.CultureRepository

class DetailMarketplaceViewModel(private val cultureRepository: CultureRepository): ViewModel() {
    fun getDetailMarketItem(idItem: String) = cultureRepository.getDetailMarketItem(idItem)

}