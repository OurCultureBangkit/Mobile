package com.example.ourculture.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.ourculture.R
import com.example.ourculture.data.Result
import com.example.ourculture.databinding.ActivityProfileBinding
import com.example.ourculture.ui.main.MainViewModel
import com.example.ourculture.util.ViewModelFactory

class ProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            viewModel.getWhoami(user.token).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        Result.Loading -> {

                        }
                        is Result.Success -> {
                            binding.pbProfile.visibility = View.GONE
                            if (result.data.avatar == null) {
                                binding.profileImage.setImageResource(R.drawable.baseline_account_circle_24)
                            } else {
                                Glide.with(this)
                                    .load(result.data.avatar)
                                    .into(binding.profileImage)
                            }

                            binding.edUsername.setText(result.data.username)
                            binding.edEmail.setText(result.data.email)

                        }
                        is Result.Error -> {
                            binding.pbProfile.visibility = View.GONE
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
}