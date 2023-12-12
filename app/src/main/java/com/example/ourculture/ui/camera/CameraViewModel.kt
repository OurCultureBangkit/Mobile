package com.example.ourculture.ui.camera

import androidx.lifecycle.ViewModel
import com.example.ourculture.data.CultureRepository
import okhttp3.MultipartBody

class CameraViewModel(private val cultureRepository: CultureRepository): ViewModel() {
    fun uploadToDetection(file: MultipartBody.Part) = cultureRepository.uploadToDetection(file)
}