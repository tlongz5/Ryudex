package com.example.RyuDex.ui.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.RyuDex.databinding.BottomSheetReaderShowChaptersBinding
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.ui.adapter.ReaderChapterAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReaderShowChaptersBottomSheet: BottomSheetDialogFragment() {
    private var _binding: BottomSheetReaderShowChaptersBinding? = null
    val binding get() = _binding!!

    var chapterCount:Int? = null
    var chapters: List<MangaChapterDTO> = emptyList()

    var onClickChapter : ((MangaChapterDTO) -> Unit)? = null

    private val readerChaptersAdapter: ReaderChapterAdapter = ReaderChapterAdapter{ mangaChapterDTO ->
        //xử lí khi bấm vào chapters
        readerChaptersAdapter.updateChapterPicked(mangaChapterDTO)
        onClickChapter?.invoke(mangaChapterDTO)
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= BottomSheetReaderShowChaptersBinding.inflate(
            inflater,container,false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvChapterCount.text = (chapterCount?.let { it.toString() }?:"?") + " pages"

        readerChaptersAdapter.submitList(chapters)

        binding.rcvChapterList.adapter = readerChaptersAdapter
        binding.rcvChapterList.layoutManager = LinearLayoutManager(context)
    }

    fun updateChapterPicked(chapter: MangaChapterDTO){
        readerChaptersAdapter.updateChapterPicked(chapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}