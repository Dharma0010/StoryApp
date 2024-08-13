package com.example.storyapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.data.api.response.ListStory
import com.example.storyapp.databinding.ItemStoryBinding
import com.squareup.picasso.Picasso

class StoryAdapter(private val onItemClick: (String) -> Unit) :
    PagingDataAdapter<ListStory, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

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
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStory>() {
            override fun areItemsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}