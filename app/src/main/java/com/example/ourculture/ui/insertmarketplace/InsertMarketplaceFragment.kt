package com.example.ourculture.ui.insertmarketplace

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.ourculture.R
import com.example.ourculture.data.Result
import com.example.ourculture.databinding.FragmentDetectionBinding
import com.example.ourculture.databinding.FragmentInsertMarketplaceBinding
import com.example.ourculture.util.ViewModelFactory
import com.example.ourculture.util.getImageUri
import com.example.ourculture.util.reduceFileImage
import com.example.ourculture.util.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class InsertMarketplaceFragment : Fragment() {
    private var _binding: FragmentInsertMarketplaceBinding? = null
    private val binding get() = _binding!!

    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<InsertMarketplaceViewModel> {
        ViewModelFactory.getInstance(requireContext())
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

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsertMarketplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadButton.setOnClickListener {
            uploadImage()
        }

        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.cameraButton.setOnClickListener {
            startCamera()
        }

    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
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

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            binding.progressBar.visibility = View.VISIBLE
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
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
            viewModel.getSession().observe(viewLifecycleOwner) { user ->
                viewModel.uploadToMarket(user.token, multipartBody, rbHarga, rbTitle, rbDescription, rbLocation, rbStock).observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        when (result) {
                            Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_SHORT).show()
                                onItemClickCallback.onItemClicked()

                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    requireContext(),
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

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
}