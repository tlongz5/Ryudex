package com.example.RyuDex.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.RyuDex.R
import com.example.RyuDex.databinding.ItemCategoryMangaBinding
import com.example.RyuDex.model.MangaCover
import java.time.Year
import kotlin.let

class MangaCategoryAdapter(
    private val callbackClickMangaCover: (MangaCover) -> Unit
): PagingDataAdapter<MangaCover, MangaCategoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val yearNow = Year.now().value

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemCategoryMangaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        item?.let {
            holder.binding.tvTitle.text = item.title
            if(item.year!=null){
                holder.binding.tvYear.text ="\uD83D\uDD25" + item.year.toString()

                if(yearNow - item.year<=2) holder.binding.tvBadge.visibility = View.VISIBLE
                else holder.binding.tvBadge.visibility = View.GONE
            }else holder.binding.tvYear.text = "\uD83D\uDD25"

            holder.itemView.setOnClickListener {
                callbackClickMangaCover(item)
            }

            Glide.with(holder.itemView.context)
                .load(item.img)
                .placeholder(R.drawable.img_bgr)
                .centerCrop()
                .into(holder.binding.imgCover)
        }
    }

    class ViewHolder(val binding: ItemCategoryMangaBinding) : RecyclerView.ViewHolder(binding.root)

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