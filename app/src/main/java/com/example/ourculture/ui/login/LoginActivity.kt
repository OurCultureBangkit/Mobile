package com.example.ourculture.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.ourculture.R
import com.example.ourculture.util.ViewModelFactory
import com.example.ourculture.data.Result
import com.example.ourculture.data.pref.UserModel
import com.example.ourculture.databinding.ActivityLoginBinding
import com.example.ourculture.ui.main.MainActivity
import com.example.ourculture.ui.signup.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var account: GoogleSignInAccount
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mGoogleSignInClient.signOut()

        binding?.btnLogin?.setOnClickListener {
            val email = binding?.etUsername?.text.toString()
            val pwd = binding?.etPwd?.text.toString()
            viewModel.postUserLogin(email, pwd).observe(this) { result ->
                if (result != null){
                    when (result){
                        is Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            if (result.data.profile.avatar != null) {
                                viewModel.saveSession(UserModel(email, result.data.accessToken, result.data.profile.username, result.data.profile.avatar))
                            } else {
                                viewModel.saveSession(UserModel(email, result.data.accessToken, result.data.profile.username, ""))
                            }
                            Log.d("LoginActivity", "ini token nya ${result.data.accessToken} ========")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }

        binding?.tvSignUpHere?.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding?.btnSignInGoogle?.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            resultLauncher.launch(signInIntent)

        }

    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                account = task.getResult(ApiException::class.java)!!
                Log.d("LoginActivity", "${account.id} berhasil login")
                Log.d("LoginActivity", "${account.idToken} berhasil login")
                Log.d("LoginActivity", "${account.displayName} berhasil login")
                Log.d("LoginActivity", "${account.email} berhasil login")
                Log.d("LoginActivity", "${account.photoUrl} berhasil login")

                viewModel.postUserLoginGoogle(account.id, account.idToken, account.displayName, account.email, account.photoUrl?.toString()).observe(this) { resultGoogle ->
                    if (resultGoogle != null) {
                        when (resultGoogle) {
                            Result.Loading -> {
                                binding?.progressBar?.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding?.progressBar?.visibility = View.GONE
                                Log.d("LoginActivity", "Suksess ${resultGoogle.data.profile.email}")
                                viewModel.saveSession(UserModel(resultGoogle.data.profile.email, resultGoogle.data.accessToken, resultGoogle.data.profile.username, resultGoogle.data.profile.avatar))
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            is Result.Error -> {
                                binding?.progressBar?.visibility = View.GONE
                                Log.e("LoginActivity", resultGoogle.error)
                            }
                        }
                    }
                }

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Login Activity", "Google sign in failed", e)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}