@file:Suppress("DEPRECATION")

package com.example.storyapp.ui.story

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.StoryViewModelFactory
import com.example.storyapp.ViewModelFactory
import com.example.storyapp.adapter.LoadingStateAdapter
import com.example.storyapp.adapter.StoryAdapter
import com.example.storyapp.data.ResultState
import com.example.storyapp.databinding.FragmentStoryListBinding
import com.example.storyapp.ui.login.LoginViewModel


class StoryListFragment : Fragment() {

    private var _binding: FragmentStoryListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryViewModel by activityViewModels{
        StoryViewModelFactory.getInstance(
            requireActivity()
        )
    }
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(
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
        setHasOptionsMenu(true)

        setupRecyclerView()
        observeViewModel()

        binding.fabAddStory.setOnClickListener {
            findNavController().navigate(R.id.action_storyListFragment_to_addandStoryFragment)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.language_setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.maps -> {
                findNavController().navigate(R.id.action_storyListFragment_to_mapsFragment)
                true
            }
            R.id.action_logout -> {
                loginViewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        storyAdapter = StoryAdapter { story ->
            // Navigate to DetailStoryFragment
            val action = StoryListFragmentDirections.actionStoryListFragmentToDetailStoryFragment(story)
            findNavController().navigate(action)
        }

        binding.rvStory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
        }
    }

    private fun observeViewModel() {
        viewModel.stories.observe(viewLifecycleOwner) { result ->
            storyAdapter.submitData(lifecycle = lifecycle, result)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}