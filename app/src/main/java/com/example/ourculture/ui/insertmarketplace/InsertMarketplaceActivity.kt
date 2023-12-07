package com.example.ourculture.ui.insertmarketplace

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.ourculture.R
import com.example.ourculture.data.Result
import com.example.ourculture.databinding.ActivityInsertMarketplaceBinding
import com.example.ourculture.util.ViewModelFactory
import com.example.ourculture.util.getImageUri
import com.example.ourculture.util.reduceFileImage
import com.example.ourculture.util.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class InsertMarketplaceActivity : AppCompatActivity() {
    private var _binding: ActivityInsertMarketplaceBinding? = null
    private val binding get() = _binding!!

    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<InsertMarketplaceViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInsertMarketplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadButton.setOnClickListener {
            uploadImage()
        }

        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.cameraButton.setOnClickListener {
            startCamera()
        }

        binding.toolbarInsertMarketplace.title = "Insert Barang"
        setSupportActionBar(binding.toolbarInsertMarketplace)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            binding.progressBar.visibility = View.VISIBLE
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val harga = binding.etPrice.text.toString()
            val title = binding.etName.text.toString()
            val description = binding.etDesc.text.toString()
            val location = binding.etLocation.text.toString()
            val stock = binding.etStock.text.toString()
            val rbHarga = harga.toRequestBody("text/plain".toMediaType())
            val rbTitle = title.toRequestBody("text/plain".toMediaType())
            val rbDescription = description.toRequestBody("text/plain".toMediaType())
            val rbLocation = location.toRequestBody("text/plain".toMediaType())
            val rbStock = stock.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )
            viewModel.getSession().observe(this) { user ->
                viewModel.uploadToMarket(user.token, multipartBody, rbHarga, rbTitle, rbDescription, rbLocation, rbStock).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(this, result.data.message.toString(), Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
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
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    interface OnItemClickCallback {
        fun onItemClicked()
    }

    companion object {
        private lateinit var onItemClickCallback: OnItemClickCallback
        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
            this.onItemClickCallback = onItemClickCallback
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        onItemClickCallback.onItemClicked()
        _binding = null
    }
}