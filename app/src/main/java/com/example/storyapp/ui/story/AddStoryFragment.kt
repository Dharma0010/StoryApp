package com.example.storyapp.ui.story

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.storyapp.R
import com.example.storyapp.StoryViewModelFactory
import com.example.storyapp.data.ResultState
import com.example.storyapp.databinding.FragmentAddStoryBinding
import com.example.storyapp.getImageUri
import com.example.storyapp.reduceFileImage
import com.example.storyapp.uriToFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

class AddStoryFragment : Fragment() {

    private var _binding: FragmentAddStoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryViewModel by viewModels {
        StoryViewModelFactory.getInstance(requireActivity())
    }
    private var currentImageUri: Uri? = null

    private var latitude: Float = 0f
    private var longitude: Float = 0f

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_story, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddStoryBinding.bind(view)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.shareLocSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkLocationPermissionAndGetLocation { location ->
                    if (location != null) {
                        latitude = location.latitude.toFloat()
                        longitude = location.longitude.toFloat()
                    }
                }
            }

        }.also {
            binding.uploadButton.setOnClickListener {
            uploadStory()
                viewModel.addStory.observe(viewLifecycleOwner) { result ->
                    when(result) {
                        is ResultState.Error -> {
                            showLoading(false)
                            showToast(result.error)
                        }
                        is ResultState.Loading -> showLoading(true)
                        is ResultState.Success -> {
                            showLoading(false)
                            showToast(result.data.message)
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }

        binding.cameraButton.setOnClickListener { startCamera() }
        binding.galleryButton.setOnClickListener { startGallery() }


    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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
    private fun startCamera() {
        currentImageUri = getImageUri(requireActivity())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }
    private fun handleUploadStory(lat: Float? = null, lon: Float? = null) {
        val description = binding.editTextDescription.text.toString()
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            viewModel.uploadStory(imageFile, description, lat, lon)
            showToast("lat: $lat lon: $lon")
        }
    }

    private fun uploadStory() {
        val locationChecked = binding.shareLocSwitch.isChecked
        if (locationChecked) {
            handleUploadStory(lat = latitude, lon = longitude)
        } else {
            handleUploadStory()
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
        }
    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun checkLocationPermissionAndGetLocation(callback: (Location?) -> Unit) {
        if (
            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token)
                .addOnCompleteListener(requireActivity()) {
                    val location: Location? = it.result
                    callback(location)
            }
        } else {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}