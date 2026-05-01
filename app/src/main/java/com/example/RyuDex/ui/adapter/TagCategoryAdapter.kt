package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.databinding.ItemCategoryTagBinding

class TagCategoryAdapter(
    private val defaultTag: String?,
    private val tags: List<Pair<String?,String>>,
    private val onClickTag: (String?) -> Unit
): RecyclerView.Adapter<TagCategoryAdapter.ViewHolder>() {

    private var selectedPosition = tags.indexOfFirst { it.first == defaultTag }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemCategoryTagBinding.inflate(
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
        holder.binding.tvTagName.text = tags[position].second
        holder.binding.root.setOnClickListener {
            selectedTagId(tags[position].first)
            onClickTag(tags[position].first)
        }

        holder.binding.root.isSelected = position == selectedPosition
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    class ViewHolder(val binding: ItemCategoryTagBinding) : RecyclerView.ViewHolder(binding.root)
    private fun selectedTagId(tagId: String?){
        selectedPosition = tags.indexOfFirst { it.first == tagId }
        notifyDataSetChanged()
    }
}