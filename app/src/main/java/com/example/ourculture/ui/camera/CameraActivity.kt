package com.example.ourculture.ui.camera

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.ourculture.data.Result
import com.example.ourculture.databinding.ActivityCameraBinding
import com.example.ourculture.databinding.DialogProgressBinding
import com.example.ourculture.ui.detection.DetectionActivity
import com.example.ourculture.ui.insertmarketplace.InsertMarketplaceViewModel
import com.example.ourculture.util.ViewModelFactory
import com.example.ourculture.util.createCustomTempFile
import com.example.ourculture.util.reduceFileImage
import com.example.ourculture.util.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class CameraActivity : AppCompatActivity() {
    private var _binding: ActivityCameraBinding? = null
    private val binding get() = _binding!!

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    private lateinit var mProgressDialog: Dialog


    private val viewModel by viewModels<CameraViewModel> {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector.equals(CameraSelector.DEFAULT_BACK_CAMERA)) CameraSelector.DEFAULT_FRONT_CAMERA
            else CameraSelector.DEFAULT_BACK_CAMERA

            startCamera()
        }

        binding.captureImage.setOnClickListener {
            takePhoto()
        }

        binding.ivGalerry.setOnClickListener {
            startGallery()

        }

    }

    fun showProgressDialog() {
        mProgressDialog = Dialog(this)

        val binding = DialogProgressBinding.inflate(layoutInflater)
        mProgressDialog.setContentView(binding.root)

        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }



    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            detectImage(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }


    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun detectImage(uri: Uri) {
        val imageFile = uriToFile(uri, this).reduceFileImage()
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )
        viewModel.uploadToDetection(multipartBody).observe(this) { result ->
            if (result != null) {
                when (result) {
                    Result.Loading -> {
                        //binding.pbCamera.visibility = View.VISIBLE
                        showProgressDialog()
                    }
                    is Result.Success -> {
                        //binding.pbCamera.visibility = View.GONE
                        hideProgressDialog()
//                            intent.putExtra(EXTRA_CAMERAX_IMAGE, uri.toString())
                        intent.putExtra(EXTRA_IMAGE_URL, result.data.image)
                        intent.putExtra(EXTRA_OUTPUT_NAME, result.data.name)
                        intent.putExtra(EXTRA_ID_DETECT, result.data.id)
                        setResult(CAMERAX_RESULT, intent)
                        finish()

                    }
                    is Result.Error -> {
                        //binding.pbCamera.visibility = View.GONE
                        hideProgressDialog()
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    detectImage(output.savedUri!!)

//                    val intent = Intent()
//                    intent.putExtra(EXTRA_CAMERAX_IMAGE, output.savedUri.toString())
//                    setResult(CAMERAX_RESULT, intent)
//                    finish()
                }
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "CameraActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
        const val EXTRA_OUTPUT_NAME = "extra_output_name"
        const val EXTRA_IMAGE_URL = "extra_image_url"
        const val EXTRA_ID_DETECT = "extra_id_detect"
    }

}