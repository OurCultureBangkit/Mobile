package com.example.ourculture.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ourculture.R
import com.example.ourculture.data.Result
import com.example.ourculture.databinding.FragmentHomeBinding
import com.example.ourculture.ui.main.LoadingStateAdapter
import com.example.ourculture.ui.main.MainAdapter
import com.example.ourculture.util.ViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mainAdapter = MainAdapter()
        binding.rvItemCulture.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = mainAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    mainAdapter.retry()
                }
            )
        }

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                viewModel.getAllCulture(user.token).observe(viewLifecycleOwner) {
                    mainAdapter.submitData(lifecycle, it)
                    binding.progressHome.visibility = View.GONE
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