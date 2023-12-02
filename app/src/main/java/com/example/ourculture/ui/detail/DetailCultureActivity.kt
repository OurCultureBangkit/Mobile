package com.example.ourculture.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ourculture.R
import com.example.ourculture.data.Result
import com.example.ourculture.databinding.ActivityDetailCultureBinding
import com.example.ourculture.util.ViewModelFactory


class DetailCultureActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailCultureViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var binding: ActivityDetailCultureBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCultureBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val idUser = intent.getStringExtra(EXTRA_ID)

        viewModel.getSession().observe(this) { user ->

            viewModel.getDetailStories(user.token ,idUser.toString()).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            binding?.root?.let {
                                binding?.ivDetailPhoto?.let { it1 ->
                                    Glide.with(it.context)
                                        .load(result.data.story?.photoUrl)
                                        .into(it1)
                                }
                            }
                            binding?.tvTitle?.text = result.data.story?.name.toString()
                            binding?.tvDescription?.text = result.data.story?.description.toString()
                        }
                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
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
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                viewModel.logout()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    companion object {
        const val EXTRA_ID = "extra_id"

    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}