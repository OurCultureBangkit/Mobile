package com.example.ourculture.ui.marketplace

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ourculture.databinding.FragmentMarketplaceBinding
import com.example.ourculture.ui.main.LoadingStateAdapter
import com.example.ourculture.ui.main.MainAdapter
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
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMarketplaceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val marketplaceAdapter = MarketplaceAdapter()
        binding.rvItemMarketplace.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = marketplaceAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    marketplaceAdapter.retry()
                }
            )
        }

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                viewModel.getAllCulture(user.token).observe(requireActivity()) {
                    marketplaceAdapter.submitData(lifecycle, it)
                    binding.progressMarketplace.visibility = View.GONE
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