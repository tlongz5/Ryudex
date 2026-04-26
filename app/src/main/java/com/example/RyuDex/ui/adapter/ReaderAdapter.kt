package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.RyuDex.R
import com.example.RyuDex.databinding.ItemReaderBinding
import com.example.RyuDex.model.ChapterImages
import com.example.RyuDex.model.MangaPage

class ReaderAdapter : ListAdapter<MangaPage, ReaderAdapter.ViewHolder>(DiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemReaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        val imgLink = item.imageUrl
        Glide.with(holder.binding.root)
            .load(imgLink)
            .placeholder(R.drawable.img_bgr)
            .into(holder.binding.imgContent)
    }

    class ViewHolder(val binding: ItemReaderBinding) : RecyclerView.ViewHolder(binding.root)


    companion object{
        val DiffUtil = object : androidx.recyclerview.widget.DiffUtil.ItemCallback<MangaPage>(){
            override fun areItemsTheSame(
                oldItem: MangaPage,
                newItem: MangaPage
            ): Boolean {
                return oldItem.pageIdx == newItem.pageIdx
            }
            override fun areContentsTheSame(
                oldItem: MangaPage,
                newItem: MangaPage
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}