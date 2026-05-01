package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.RyuDex.R
import com.example.RyuDex.databinding.ItemFeaturedMangaBinding
import com.example.RyuDex.model.MangaCover
import kotlin.math.min

class MangaFeaturedAdapter(
    private val hotList: List<MangaCover>,
    private val onClick: (MangaCover) -> Unit
): RecyclerView.Adapter<MangaFeaturedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemFeaturedMangaBinding.inflate(
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
        holder.binding.tvStats.text = hotList[position].year?.toString() ?: "Unknown"
        holder.binding.tvRank.text = (position+1).toString()
        holder.binding.tvTitle.text = hotList[position].title
        holder.itemView.setOnClickListener {
            onClick(hotList[position])
        }
        Glide.with(holder.itemView.context)
            .load(hotList[position].img)
            .placeholder(R.drawable.img_bgr)
            .centerCrop()
            .into(holder.binding.imgManga)

        val tags = listOf(holder.binding.tag1,holder.binding.tag2,holder.binding.tag3)

        for(i in 0 until min(hotList[position].category.size,3)){
            tags[i].visibility = View.VISIBLE
            tags[i].text = hotList[position].category[i].second
        }
        for(i in min(hotList[position].category.size,3) until 3){
            tags[i].visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return hotList.size
    }

    class ViewHolder(val binding: ItemFeaturedMangaBinding) : RecyclerView.ViewHolder(binding.root)
}