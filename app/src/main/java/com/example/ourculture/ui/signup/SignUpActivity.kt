package com.example.ourculture.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.ourculture.R
import com.example.ourculture.data.Result
import com.example.ourculture.databinding.ActivitySignUpBinding
import com.example.ourculture.ui.login.LoginActivity
import com.example.ourculture.util.ViewModelFactory

class SignUpActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val username = binding.etSignUpUsername.text.toString()
            val password = binding.etSignUpPwd.text.toString()
            val repeatPassword = binding.etSignUpPwdRpt.text.toString()
            val email = binding.etEmailSignUp.text.toString()
            viewModel.postUserRegister(username, password, repeatPassword, email).observe(this) { result ->
               if (result != null) {
                   when (result) {
                       Result.Loading -> {
                           binding.progressBar.visibility = View.VISIBLE
                       }
                       is Result.Success -> {
                           binding.progressBar.visibility = View.GONE
                           Toast.makeText(this, "Akun Berhasil di buat", Toast.LENGTH_SHORT).show()
                           startActivity(Intent(this, LoginActivity::class.java))
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
}