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

    private var _binding: ActivityDetailCultureBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailCultureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val idCulture = intent.getIntExtra(EXTRA_ID, -1)
        viewModel.getDetailCulture(idCulture).observe(this) { result ->
            if (result != null) {
                when (result) {
                    Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvTitle.text = result.data.name
                        Glide.with(this)
                            .load(result.data.image)
                            .into(binding.ivDetailPhoto)
                        binding.tvDescription.text = result.data.description
                        binding.ivDetailPhoto.visibility = View.VISIBLE
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.ivDetailPhoto.visibility = View.VISIBLE
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
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
        _binding = null
    }
}