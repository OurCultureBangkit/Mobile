package com.example.ourculture.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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
            val username = binding.etSignUpUsername.text.toString().trim()
            val password = binding.etSignUpPwd.text.toString().trim()
            val repeatPassword = binding.etSignUpPwdRpt.text.toString().trim()
            val email = binding.etEmailSignUp.text.toString().trim()

            if (validateForm(username, email, password, repeatPassword)) {
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

        binding.tvSignUpHere.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validateForm(userName: String, email:String, password: String, rptPassword: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(userName) -> {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(password) -> {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                false
            }
            rptPassword != password -> {
                Toast.makeText(this, "Password and confirmation Password do not match", Toast.LENGTH_SHORT).show()
                false
            }
            else -> { true }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}