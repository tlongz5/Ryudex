package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.databinding.ItemHomeTitleBinding

class HomeTitleAdapter(private val title: String) :
    RecyclerView.Adapter<HomeTitleAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemHomeTitleBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemHomeTitleBinding.inflate(
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
        holder.binding.tvTitle.text = title
    }

    override fun getItemCount(): Int {
        return 1
    }
}