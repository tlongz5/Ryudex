package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.RyuDex.databinding.ItemHomeMangaListBinding
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.MangaItem

class HomeMangaListAdapter(private val callbackClickMangaCover: (MangaCover) -> Unit) : PagingDataAdapter<MangaCover, HomeMangaListAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(val binding: ItemHomeMangaListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemHomeMangaListBinding.inflate(
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
        item?.let {
            holder.binding.tvAuthor.text = item.author.second
            holder.binding.tvTitle.text = item.title
            holder.binding.tvDescription.text = item.description
            Glide.with(holder.itemView.context)
                .load(item.img)
                .centerCrop()
                .into(holder.binding.imgCover)

            holder.itemView.setOnClickListener {
                callbackClickMangaCover(item)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MangaCover>(){
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