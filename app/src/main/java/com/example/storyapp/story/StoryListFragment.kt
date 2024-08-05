package com.example.storyapp.story

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.StoryViewModelFactory
import com.example.storyapp.ViewModelFactory
import com.example.storyapp.adapter.StoryAdapter
import com.example.storyapp.data.ResultState
import com.example.storyapp.databinding.FragmentStoryListBinding

class StoryListFragment : Fragment() {

    private var _binding: FragmentStoryListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryViewModel by viewModels{
        StoryViewModelFactory.getInstance(
            requireActivity()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story_list, container, false)
    }

    private lateinit var storyAdapter: StoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStoryListBinding.bind(view)

        setupRecyclerView()
        observeViewModel()


    }

    private fun setupRecyclerView() {
        storyAdapter = StoryAdapter { story ->
            // Navigate to DetailStoryFragment
            findNavController().navigate(R.id.action_storyListFragment_to_addandUpdateStoryFragment)
        }

        binding.rvStory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = storyAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.getStories().observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> showLoading(true)
                is ResultState.Success -> {
                    showLoading(false)
                    storyAdapter.setStories(result.data)
                }
                is ResultState.Error -> {
                    showLoading(false)
//                    Toast.makeText(requireContext(), "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                    Log.e("StoryListFragment", "Error: ${result.error}")
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}