//package com.example.RyuDex.ui.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.RyuDex.databinding.ItemHomeTitleBinding
//
//class HistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): RecyclerView.ViewHolder {
//        return when(viewType){
//            TYPE_HEADER -> {
//                val binding = ItemHomeTitleBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//                HeaderViewHolder(binding)
//            }
//
//            else -> {
//                val binding = ItemHomeMangaBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//                MangaViewHolder(binding)
//            }
//        }
//    }
//
//    override fun onBindViewHolder(
//        holder: RecyclerView.ViewHolder,
//        position: Int
//    ) {
//        i
//    }
//
//    companion object{
//        const val TYPE_HEADER = 0
//        const val TYPE_MANGA = 1
//    }
//
//
//
//    override fun getItemCount(): Int {
//
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return super.getItemViewType(position)
//        if (position == 0) {
//            return TYPE_HEADER
//        }
//        return TYPE_MANGA
//    }
//}