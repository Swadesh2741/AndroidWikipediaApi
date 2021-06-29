package com.example.wikipediaapitest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wikipediaapitest.R
import com.example.wikipediaapitest.data.model.WikiPages
import com.example.wikipediaapitest.databinding.ListItemLayoutBinding

class WikiListAdapter() : ListAdapter<WikiPages, WikiListAdapter.WikiListViewHolder>(DiffUtils) {

    private var onContainerClickListener: ((WikiPages) -> Unit)? = null
    fun setOnContainerClickListener(listener: (WikiPages) -> Unit) {
        onContainerClickListener = listener
    }

    inner class WikiListViewHolder(private val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wikiPages: WikiPages) {
            binding.apply {
                ivProfile.load(wikiPages.thumbnail?.source) {
                    fallback(R.drawable.profile_placeholder)
                }
                tvTitle.text = wikiPages.title;
                tvDescription.text = wikiPages.terms?.description?.get(0)
            }
            binding.container.setOnClickListener {
                onContainerClickListener?.let {
                    it(wikiPages)
                }
            }
        }
    }

    object DiffUtils : DiffUtil.ItemCallback<WikiPages>() {
        override fun areItemsTheSame(oldItem: WikiPages, newItem: WikiPages): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: WikiPages, newItem: WikiPages): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: WikiListViewHolder, position: Int) {
        val wikiData = getItem(position)
        if (wikiData != null) {
            holder.bind(wikiData)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WikiListViewHolder {
        return WikiListViewHolder(
            ListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}