package com.example.RyuDex.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.RyuDex.databinding.ItemDownloadDoneDetailBinding
import com.example.RyuDex.model.entity.MangaCoverEntity

class DownloadDoneDetailAdapter: ListAdapter<MangaCoverEntity, DownloadDoneDetailAdapter.ViewHolder>(DIFF_UTIL) {
    class ViewHolder(val binding: ItemDownloadDoneDetailBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDownloadDoneDetailBinding.inflate(
            android.view.LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            tvTitle.text = item.title
            Glide.with(holder.itemView.context)
                .load(item.imgOnline)
                .into(imgCover)
        }
    }
    companion object{
        val DIFF_UTIL = object : androidx.recyclerview.widget.DiffUtil.ItemCallback<MangaCoverEntity>(){
            override fun areItemsTheSame(
                oldItem: MangaCoverEntity,
                newItem: MangaCoverEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: MangaCoverEntity,
                newItem: MangaCoverEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}