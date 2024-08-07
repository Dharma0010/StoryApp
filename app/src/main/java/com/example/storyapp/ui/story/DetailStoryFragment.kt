package com.example.storyapp.ui.story

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.storyapp.R
import com.example.storyapp.StoryViewModelFactory
import com.example.storyapp.data.ResultState
import com.example.storyapp.databinding.FragmentDetailStoryBinding
import com.squareup.picasso.Picasso


class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!

    val args : DetailStoryFragmentArgs by navArgs()

    private val viewModel: StoryViewModel by viewModels {
        StoryViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_story, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailStoryBinding.bind(view)

        val id = args.id
        viewModel.getDetailStory(id).observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> showLoading(true)
                is ResultState.Success -> {
                    showLoading(false)
                    val story = result.data.story
                    binding.apply {
                        tvName.text = story?.name.toString()
                        tvDescription.text = story?.description.toString()
                        Picasso.get().load(story?.photoUrl).into(ivStoryPhoto)
                    }
                }
                is ResultState.Error -> {
                    showLoading(false)
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.ivStoryPhoto.isEnabled = !isLoading
        binding.tvDescription.isEnabled = !isLoading
        binding.tvName.isEnabled = !isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}