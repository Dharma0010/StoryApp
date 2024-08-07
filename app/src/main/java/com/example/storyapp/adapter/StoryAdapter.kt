package com.example.storyapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.data.api.response.ListStory
import com.example.storyapp.databinding.ItemStoryBinding
import com.squareup.picasso.Picasso

class StoryAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private val stories = mutableListOf<ListStory>()


    @SuppressLint("NotifyDataSetChanged")
    fun setStories(newStories: List<ListStory>) {
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

        fun bind(listStory: ListStory) {
            binding.apply {
                tvName.text = listStory.name
                tvDesc.text = listStory.description
                Picasso.get().load(listStory.photoUrl).into(ivStoryItem)
                cvItemStory.setOnClickListener { onItemClick(listStory.id.toString()) }
            }
        }
    }
}