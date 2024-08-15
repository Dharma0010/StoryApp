package com.example.storyapp.ui.maps

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.storyapp.R
import com.example.storyapp.StoryViewModelFactory
import com.example.storyapp.data.ResultState
import com.example.storyapp.databinding.FragmentMapsBinding
import com.example.storyapp.ui.story.StoryViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap

    private val viewModel: StoryViewModel by viewModels {
        StoryViewModelFactory.getInstance(requireActivity())
    }
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        viewModel.getLocationStories().observe(viewLifecycleOwner) { location ->
            when (location) {
                is ResultState.Error -> {}
                ResultState.Loading -> {}
                is ResultState.Success -> location.data.listStory.forEach {
                    mMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(it.lat!!, it.lon!!))
                            .title(it.name)
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.lat, it.lon)))
                }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}