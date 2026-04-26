package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.RyuDex.databinding.ItemRelatedMangaBinding
import com.example.RyuDex.model.MangaCover

class RelatedMangaAdapter(
    private val onClick: (MangaCover) -> Unit
): ListAdapter<MangaCover,RelatedMangaAdapter.ViewHolder>(DIFF_UTIL){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRelatedMangaBinding.inflate(
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
        holder.binding.tvTitle.text = item.title
        holder.binding.tvAuthor.text = item.author.second
        Glide.with(holder.itemView.context)
            .load(item.img)
            .centerCrop()
            .into(holder.binding.imgCover)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class ViewHolder(val binding: ItemRelatedMangaBinding) : RecyclerView.ViewHolder(binding.root)

    companion object{
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MangaCover>(){
            override fun areItemsTheSame(
                oldItem: MangaCover,
                newItem: MangaCover
            ): Boolean {
                return oldItem.id==newItem.id
            }
            override fun areContentsTheSame(
                oldItem: MangaCover,
                newItem: MangaCover
            ): Boolean {
                return oldItem==newItem
            }
        }
    }
}