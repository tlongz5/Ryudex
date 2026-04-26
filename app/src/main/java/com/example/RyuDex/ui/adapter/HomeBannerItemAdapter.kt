package com.example.RyuDex.ui.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.RyuDex.databinding.ItemHomeBannerBinding
import com.example.RyuDex.model.MangaCover
import jp.wasabeef.glide.transformations.BlurTransformation

class HomeBannerItemAdapter(private val callback:(MangaCover) -> Unit): ListAdapter<MangaCover, HomeBannerItemAdapter.ViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemHomeBannerBinding.inflate(
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
        holder.binding.tvDescription.text = item.description
        holder.binding.tvChapter.text = "Chap ${item.lastChapter}"
        Glide.with(holder.itemView)
            .load(item.img)
            .transform(CenterCrop(),BlurTransformation(25))
            .into(holder.binding.imgBlurCover)

        Glide.with(holder.itemView)
            .load(item.img)
            .centerCrop()
            .into(holder.binding.imgCover)

        holder.itemView.setOnClickListener {
            callback(item)
        }
    }

    class ViewHolder(val binding: ItemHomeBannerBinding) : RecyclerView.ViewHolder(binding.root)

    companion object{
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MangaCover>(){
            override fun areItemsTheSame(
                oldItem: MangaCover,
                newItem: MangaCover
            ): Boolean {
                return oldItem.id == newItem.id
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