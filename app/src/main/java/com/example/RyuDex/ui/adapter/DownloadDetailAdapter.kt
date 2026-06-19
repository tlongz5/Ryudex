package com.example.RyuDex.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.RyuDex.databinding.ItemDownloadDetailBinding
import com.example.RyuDex.model.entity.DownloadStatus
import com.example.RyuDex.model.entity.MangaWithChapters

class DownloadDetailAdapter(
    private val onItemClicked: (MangaWithChapters) -> Unit,
    private val onContinueClicked: (MangaWithChapters) -> Unit,
    private val onPauseClicked: (MangaWithChapters) -> Unit,
    private val onCancelClicked: (MangaWithChapters) -> Unit
) : ListAdapter<MangaWithChapters, DownloadDetailAdapter.ViewHolder>(DIFF_UTIL) {

    class ViewHolder(val binding: ItemDownloadDetailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDownloadDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        val manga = item.manga
        val chapters = item.chapters

        holder.binding.apply {
            btnContinue.setOnClickListener { onContinueClicked(item) }
            btnPause.setOnClickListener { onPauseClicked(item) }
            btnCancel.setOnClickListener { onCancelClicked(item) }
            root.setOnClickListener { onItemClicked(item) }

            tvStatus.text = manga.downloadStatus.toString()
            tvInfo.visibility = View.VISIBLE
            btnContinue.visibility = View.GONE
            btnPause.visibility = View.VISIBLE
            btnCancel.visibility = View.VISIBLE
            tvMangaTitle.text = manga.title

            val downloadedPages = chapters.sumOf { it.downloadedPages }
            val totalPage = chapters.mapNotNull { it.totalPages }.sum()

            if(manga.downloadStatus == DownloadStatus.PENDING || manga.downloadStatus == DownloadStatus.PAUSED){
                btnContinue.visibility = View.VISIBLE
                btnPause.visibility = View.GONE
            }

            if(downloadedPages == totalPage) {
                btnContinue.visibility = View.GONE
                btnPause.visibility = View.GONE
                btnCancel.visibility = View.GONE
            }

            if (totalPage > 0) {
                tvInfo.visibility = View.GONE
                val progressPercent = (downloadedPages * 100.0) / totalPage

                tvProgress.text = "${"%.2f".format(progressPercent)}%"

                progressDownload.max = totalPage
                progressDownload.progress = downloadedPages
            } else {
                progressDownload.max = 100
                progressDownload.progress = 0
            }

            val imageToLoad = manga.imgLocal ?: manga.imgOnline

            Glide.with(holder.itemView.context)
                .load(imageToLoad)
                .into(imgCover)
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MangaWithChapters>() {
            override fun areItemsTheSame(
                oldItem: MangaWithChapters,
                newItem: MangaWithChapters
            ): Boolean {
                return oldItem.manga.id == newItem.manga.id
            }

            override fun areContentsTheSame(
                oldItem: MangaWithChapters,
                newItem: MangaWithChapters
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
