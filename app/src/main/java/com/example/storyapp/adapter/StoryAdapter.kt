package com.example.storyapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.data.api.response.Story
import com.example.storyapp.databinding.ItemStoryBinding
import com.squareup.picasso.Picasso

class StoryAdapter(private val onItemClick: (Story) -> Unit) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private val stories = mutableListOf<Story>()

    @SuppressLint("NotifyDataSetChanged")
    fun setStories(newStories: List<Story>) {
        stories.clear()
        stories.addAll(newStories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(stories[position])
    }

    override fun getItemCount() = stories.size

    inner class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: Story) {
            binding.apply {
                tvName.text = story.name
                tvDesc.text = story.description
                Picasso.get().load(story.photoUrl).into(ivStoryItem)
                cvItemStory.setOnClickListener { onItemClick(story) }
            }
        }
    }
}