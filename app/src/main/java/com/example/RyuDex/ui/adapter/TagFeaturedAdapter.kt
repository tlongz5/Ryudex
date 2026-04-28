package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.databinding.ItemFeaturedTagBinding

class TagFeaturedAdapter(
    private val hotTag: List<Pair<String,String?>>,
    private val onClickTag: (String?) -> Unit
): RecyclerView.Adapter<TagFeaturedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemFeaturedTagBinding.inflate(
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
        holder.binding.tvTag.text = "#" + hotTag[position].first
        holder.binding.root.setOnClickListener {
            onClickTag(hotTag[position].second)
        }
    }

    override fun getItemCount(): Int {
        return hotTag.size
    }

    class ViewHolder(val binding: ItemFeaturedTagBinding) : RecyclerView.ViewHolder(binding.root)




}