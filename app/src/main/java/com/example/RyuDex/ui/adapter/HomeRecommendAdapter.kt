package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.RyuDex.databinding.ItemHomeRecommendBinding
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.utils.Constant
import com.google.android.material.tabs.TabLayout

class HomeRecommendAdapter(
    private val callbackClickTab: (String?)-> Unit,
    private val callbackClickMangaCover: (MangaCover) -> Unit
) :
    RecyclerView.Adapter<HomeRecommendAdapter.ViewHolder>() {

    private val adapter = HomeRecommendMangaAdapter{ mangaCover ->
        // chuyển sang màn tiếp theo khi user click
        callbackClickMangaCover(mangaCover)
    }

    class ViewHolder(val binding: ItemHomeRecommendBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemHomeRecommendBinding.inflate(
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
        Constant.POPULAR_TAGS.forEach {
            val newTab = holder.binding.tabLayout.newTab()
            newTab.text = it.value
            newTab.tag = it.key
            holder.binding.tabLayout.addTab(newTab)
        }

        holder.binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tag = tab?.tag as? String
                callbackClickTab(tag)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        holder.binding.rcvRecommend.adapter = adapter
        holder.binding.rcvRecommend.layoutManager = GridLayoutManager(holder.itemView.context, 3, GridLayoutManager.HORIZONTAL, false)
    }

    override fun getItemCount(): Int {
        return 1
    }

    //Chuyển data khác khi user chuyển tab khác
    fun updateData(mangaCoverList: List<MangaCover>){
        adapter.submitList(mangaCoverList)
    }

}