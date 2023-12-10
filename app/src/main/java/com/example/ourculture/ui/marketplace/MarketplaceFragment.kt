package com.example.ourculture.ui.marketplace

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ourculture.data.Result
import com.example.ourculture.databinding.FragmentMarketplaceBinding
import com.example.ourculture.util.ViewModelFactory

class MarketplaceFragment : Fragment() {
    private var _binding: FragmentMarketplaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MarketplaceViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMarketplaceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val marketplaceAdapter = MarketplaceAdapter()
        binding.rvItemMarketplace.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }

        binding.rvItemMarketplace.adapter = marketplaceAdapter
        viewModel.barang.observe(viewLifecycleOwner) {
            marketplaceAdapter.submitData(lifecycle, it)
        }

        MarketplacePagingSource.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }


        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressMarketplace.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}