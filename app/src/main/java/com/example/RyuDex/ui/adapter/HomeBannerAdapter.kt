package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.databinding.ItemHomeBannerPagerBinding
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.ui.anim.PageTransformer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeBannerAdapter(
    private val onClickBanner: (MangaCover) -> Unit
): RecyclerView.Adapter<HomeBannerAdapter.ViewHolder>() {

    private val homeBannerItemAdapter = HomeBannerItemAdapter{ mangaCover ->
        onClickBanner(mangaCover)
    }

    class ViewHolder(val binding: ItemHomeBannerPagerBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemHomeBannerPagerBinding.inflate(
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
        holder.binding.viewPager.adapter = homeBannerItemAdapter
        holder.binding.viewPager.setPageTransformer(PageTransformer())
        holder.binding.viewPager.offscreenPageLimit = 3
    }

    override fun getItemCount(): Int {
        return 1
    }

    fun updateList(listBanner: List<MangaCover>){
        homeBannerItemAdapter.submitList(listBanner)
    }

}