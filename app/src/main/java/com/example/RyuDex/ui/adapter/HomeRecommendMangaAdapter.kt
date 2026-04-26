package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.RyuDex.databinding.ItemHomeMangaRecommendBinding
import com.example.RyuDex.model.MangaCover
import jp.wasabeef.glide.transformations.BlurTransformation

class HomeRecommendMangaAdapter(private val onClick:(MangaCover)-> Unit) :
    ListAdapter<MangaCover,HomeRecommendMangaAdapter.ViewHolder>(DIFF_UTIL) {
    class ViewHolder(val binding: ItemHomeMangaRecommendBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemHomeMangaRecommendBinding.inflate(
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
        holder.binding.tvRank.text = (position+1).toString()
        holder.binding.tvAuthor.text= item.author.second
        val category = item.category[0].second
        holder.binding.tvCategory.text = category
        Glide.with(holder.itemView.context)
            .load(item.img)
            .centerCrop()
            .into(holder.binding.imgCover)

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    companion object{
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<MangaCover>(){
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