package com.example.ourculture.ui.detection

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.ourculture.R
import com.example.ourculture.databinding.FragmentDetectionBinding
import com.example.ourculture.ui.camera.CameraActivity
import com.example.ourculture.ui.detail.DetailCultureActivity

class DetectionFragment : Fragment() {
    private var _binding: FragmentDetectionBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetectionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCameraX()
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CameraActivity.CAMERAX_RESULT) {
            //currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            binding.tvDetectionLabel.text = it.data?.getStringExtra(CameraActivity.EXTRA_OUTPUT_NAME).toString()
            Glide.with(this)
                .load(it.data?.getStringExtra(CameraActivity.EXTRA_IMAGE_URL))
                .into(binding.ivDetectImage)

            val idCulture = it.data?.getIntExtra(CameraActivity.EXTRA_ID_DETECT, -1)

            binding.btnPelajariSelengkapnya.setOnClickListener {
                val intent = Intent(requireActivity(), DetailCultureActivity::class.java)
                intent.putExtra(DetailCultureActivity.EXTRA_ID, idCulture)
                startActivity(intent)
            }
            //showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivDetectImage.setImageURI(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}