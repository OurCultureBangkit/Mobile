package com.example.ourculture.ui.detailmarketplace

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ourculture.R
import com.example.ourculture.data.Result
import com.example.ourculture.data.remote.retrofit.response.CommmentsItem
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

        binding.rvItemComment.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }


        viewModel.isLoading.observe(this) {
            showLoading(it)
        }


        viewModel.getSession().observe(this) { user ->
            Glide.with(this)
                .load(user.avatar)
                .error(R.drawable.baseline_account_circle_24)
                .into(binding.profileImageDetail)

            binding.profileImageDetail
            binding.ibSendComment.setOnClickListener {
                viewModel.postComment(user.token, idItem, binding.etAddComment.text.toString())
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                binding.etAddComment.setText("")
                binding.etAddComment.clearFocus()
            }

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
                            binding.ibSendComment.visibility = View.VISIBLE
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

                            viewModel.getCommentMarketItem(user.token, idItem)
                            viewModel.listComment.observe(this) { commentItem ->
                                setReviewData(user.token, idItem, commentItem, user.username == result.data.barang.postBy.username)
                            }

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

//            viewModel.getCommentMarketItem(user.token, idItem).observe(this) { result ->
//                if (result != null) {
//                    when (result) {
//                        Result.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
//                        }
//                        is Result.Success -> {
//                            binding.progressBar.visibility = View.GONE
//                            val commentAdapter = CommentAdapter(this) { comment, idcomment ->
//                                viewModel.postReplyComment(user.token, idItem, idcomment, comment).observe(this) {
//                                    if (it != null) {
//                                        when (it) {
//                                            Result.Loading -> {
//                                                binding.progressBar.visibility = View.VISIBLE
//                                            }
//                                            is Result.Success -> {
//                                                binding.progressBar.visibility = View.GONE
//                                                Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
//                                            }
//                                            is Result.Error -> {
//                                                binding.progressBar.visibility = View.GONE
//                                                Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
//                                            }
//
//                                        }
//                                    }
//                                }
//                            }
//                            binding.rvItemComment.apply {
//                                layoutManager = LinearLayoutManager(context)
//                                setHasFixedSize(true)
//                            }
//                            commentAdapter.submitList(result.data)
//                            binding.rvItemComment.adapter = commentAdapter
//                        }
//                        is Result.Error -> {
//                            binding.progressBar.visibility = View.GONE
//                        }
//
//                    }
//                }
//            }


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
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setReviewData(token: String,idItem: String, commentItem: List<CommmentsItem>, postBy: Boolean) {
        val adapter = CommentAdapter(this, postBy) { comment, idComment ->
            viewModel.postReplyComment(token, idItem, idComment, comment)
        }
        adapter.submitList(commentItem)

        binding.rvItemComment.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}