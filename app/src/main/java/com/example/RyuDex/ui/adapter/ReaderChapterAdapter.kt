package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.databinding.ItemReaderChapterBinding
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.utils.Constant.LANGUAGES

class ReaderChapterAdapter(
    private val onClickChapter: (MangaChapterDTO) -> Unit
): ListAdapter<MangaChapterDTO, ReaderChapterAdapter.ViewHolder>(DIFF_UTIL) {

    var chapterPicked: MangaChapterDTO? = null

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val binding = ItemReaderChapterBinding.inflate(
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
            val item = getItem(position)
            holder.binding.tvChapterName.text = "Chap " + item.attributes.chapter + if(item.attributes.title == null) "" else ": "+ item.attributes.title
            holder.binding.tvChapterSubInfo.text = item.attributes.translatedLanguage?.let { languageCode ->
                LANGUAGES.find { it.first == languageCode }?.second + " • "
            } + item.attributes.pages?.let { totalPage->
                "${totalPage} pages"
            }
            holder.binding.tvChapterTime.text = item.attributes.createdAt?.let { createdAt -> createdAt }
            holder.itemView.setOnClickListener {
                onClickChapter(item)
            }
            if(chapterPicked == item) holder.itemView.alpha =0.8f
        }

        class ViewHolder(val binding: ItemReaderChapterBinding) : RecyclerView.ViewHolder(binding.root)

        companion object{
            val DIFF_UTIL = object : DiffUtil.ItemCallback<MangaChapterDTO>(){
                override fun areItemsTheSame(
                    oldItem: MangaChapterDTO,
                    newItem: MangaChapterDTO
                ): Boolean {
                    return oldItem.id == newItem.id
                }
                override fun areContentsTheSame(
                    oldItem: MangaChapterDTO,
                    newItem: MangaChapterDTO
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }

    fun updateChapterPicked(chapter: MangaChapterDTO){
        val preChapterPicked = chapterPicked
        chapterPicked = chapter
        currentList.indexOf(preChapterPicked).let {
            if(it != -1) notifyItemChanged(it)
        }
        notifyItemChanged(currentList.indexOf(chapter))
    }

}