package com.example.ourculture.ui.detailmarketplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.ourculture.R
import com.example.ourculture.data.Result
import com.example.ourculture.databinding.ActivityDetailMarketplaceBinding
import com.example.ourculture.util.ViewModelFactory

class DetailMarketplaceActivity : AppCompatActivity() {
    private var _binding: ActivityDetailMarketplaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailMarketplaceViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var userToken = ""
    private var barangId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailMarketplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idItem = intent.getStringExtra(EXTRA_ID).toString()

        viewModel.getSession().observe(this) { user ->
            userToken = user.token
            viewModel.getDetailMarketItem(idItem).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            barangId = result.data.barang.id
                            binding.progressBar.visibility = View.GONE
                            binding.tvDescriptionText.text = resources.getString(R.string.deskripsi)
                            binding.tvPostBy.text = resources.getString(R.string.post_by)
                            binding.btnAddWishlist.visibility = View.VISIBLE
                            binding.comment.text = resources.getString(R.string.comment)
                            binding.profileImageDetail.visibility = View.VISIBLE
                            binding.tiAddComment.visibility = View.VISIBLE
                            binding.tvLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_location_on_18, 0, 0, 0)

                            Glide.with(this)
                                .load(result.data.barang.images)
                                .into(binding.ivDetailPhoto)
                            binding.tvTitle.text = result.data.barang.title
                            binding.tvPrice.text = "Rp.${result.data.barang.harga}"
                            binding.tvStock.text =  "Stock: ${result.data.barang.stock}"
                            binding.tvLocation.text = result.data.barang.location
                            binding.tvPostByName.text = result.data.barang.postBy.username
                            binding.tvDescription.text = result.data.barang.description
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.btnAddWishlist.setOnClickListener {
            viewModel.postUserWishlist(userToken, barangId).observe(this) { result ->
                if (result != null) {
                    when(result) {
                        Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}