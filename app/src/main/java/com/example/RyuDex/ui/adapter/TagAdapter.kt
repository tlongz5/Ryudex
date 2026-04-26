package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.databinding.ItemTagBinding

class TagAdapter(private val onClickTag : (String) -> Unit): ListAdapter<Pair<String,String>, TagAdapter.ViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.binding.tvTag.text = item.second
        holder.binding.root.setOnClickListener {
            onClickTag(item.first)
        }
    }

    class ViewHolder(val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root)

    companion object{
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Pair<String,String>>(){
            override fun areItemsTheSame(oldItem: Pair<String,String>, newItem: Pair<String,String>): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Pair<String,String>, newItem: Pair<String,String>): Boolean {
                return oldItem == newItem
            }
        }
    }
}