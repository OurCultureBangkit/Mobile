package com.example.ourculture.ui.wishlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ourculture.R
import com.example.ourculture.data.Result
import com.example.ourculture.databinding.FragmentWishlistBinding
import com.example.ourculture.util.ViewModelFactory

class WishlistFragment : Fragment() {
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<WishlistViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            viewModel.getUserWishlist(user.token).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result){
                        Result.Loading -> {

                        }
                        is Result.Success -> {
                            binding.pbWishlist.visibility = View.GONE
                            val wishlistData = result.data
                            val wishListAdapter = WishListAdapter{ barangItemWishList ->
                                viewModel.deleteUserWishlist(user.token, barangItemWishList.wishListId).observe(viewLifecycleOwner) { resultDelete ->
                                    if (resultDelete != null) {
                                        when(resultDelete) {
                                            Result.Loading -> {
                                                binding.pbWishlist.visibility = View.VISIBLE
                                            }
                                            is Result.Success -> {
                                                binding.pbWishlist.visibility = View.GONE

                                            }
                                            is Result.Error -> {
                                                binding.pbWishlist.visibility = View.GONE

                                            }
                                        }
                                    }
                                }
                            }
                            binding.rvWishlist.apply {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                            }
                            wishListAdapter.submitList(wishlistData)
                            binding.rvWishlist.adapter = wishListAdapter
                        }
                        is Result.Error -> {
                            binding.pbWishlist.visibility = View.GONE
                            binding.tvWishlistEmpty.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}