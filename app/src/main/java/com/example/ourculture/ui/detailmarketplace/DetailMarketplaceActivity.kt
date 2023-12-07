package com.example.ourculture.ui.detailmarketplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ourculture.R
import com.example.ourculture.databinding.ActivityDetailMarketplaceBinding

class DetailMarketplaceActivity : AppCompatActivity() {
    private var _binding: ActivityDetailMarketplaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailMarketplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}