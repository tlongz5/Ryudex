package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.databinding.ItemFeaturedColumnMangaBinding
import com.example.RyuDex.databinding.ItemFeaturedColumnTagBinding
import com.example.RyuDex.databinding.ItemFeaturedMangaBinding
import com.example.RyuDex.databinding.ItemFeaturedTagBinding
import com.example.RyuDex.model.MangaCover

class FeaturedAdapter(
    private var hotMangas: List<MangaCover>,
    private val hotTags: List<Pair<String,String?>>,
    private val onClickManga: (MangaCover) -> Unit,
    private val onClickTag: (String?) -> Unit,
    private val onClickExplore: () -> Unit,
    private val onClickSeeAll: () -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateHotMangas(newList: List<MangaCover>) {
        hotMangas = newList
        notifyDataSetChanged()
    }
    companion object{
        const val TYPE_HOT_TAG = 0
        const val TYPE_HOT_MANGA = 1
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return if(position == 0){
            TYPE_HOT_TAG
        }else{
            TYPE_HOT_MANGA
        }
    }

    class HotTagViewHolder(val binding: ItemFeaturedColumnTagBinding) : RecyclerView.ViewHolder(binding.root)
    class HotMangaViewHolder(val binding: ItemFeaturedColumnMangaBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            TYPE_HOT_TAG-> {
                val binding = ItemFeaturedColumnTagBinding.inflate(inflater, parent, false)
                return HotTagViewHolder(binding)
            }
            TYPE_HOT_MANGA -> {
                val binding = ItemFeaturedColumnMangaBinding.inflate(inflater, parent, false)
                return HotMangaViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when(holder){
            is HotTagViewHolder -> {
                holder.binding.rcvHotTag.adapter = TagFeaturedAdapter(hotTags,onClickTag)
                holder.binding.rcvHotTag.layoutManager = LinearLayoutManager(holder.itemView.context)
                holder.binding.btnSeeAll.setOnClickListener {
                    onClickSeeAll()
                }
            }
            is HotMangaViewHolder -> {
                holder.binding.rcvHotManga.adapter = MangaFeaturedAdapter(hotMangas, onClickManga)
                holder.binding.rcvHotManga.layoutManager = LinearLayoutManager(holder.itemView.context)
                holder.binding.btnSeeAll.setOnClickListener {
                    onClickExplore()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }


}