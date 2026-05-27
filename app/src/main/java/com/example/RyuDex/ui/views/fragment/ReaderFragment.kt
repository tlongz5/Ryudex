package com.example.RyuDex.ui.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.databinding.FragmentReaderBinding
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.UiState
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.ui.adapter.ReaderAdapter
import com.example.RyuDex.ui.viewmodel.ReaderViewModel
import com.example.RyuDex.utils.fadeInAnim
import com.example.RyuDex.utils.fadeOutAnim
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReaderFragment : Fragment() {
    private var _binding: FragmentReaderBinding? = null
    val binding get() = _binding!!

    private val safeArgs: ReaderFragmentArgs by navArgs()

    private var curChapter: MangaChapterDTO? = null

    private val readerViewModel: ReaderViewModel by viewModels()

    private var readerAdapter = ReaderAdapter { position ->
        binding.pageSeekBar.progress = position
    }

    var isControlVisible = true

    private val readerShowChaptersBottomSheet = ReaderShowChaptersBottomSheet()
    private var chapters = emptyList<MangaChapterDTO>()

    private var chaptersByLanguage = emptyList<MangaChapterDTO>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chapter = safeArgs.chapter
        val mangaCover = safeArgs.mangaCover

        curChapter = chapter

        setupView(chapter, mangaCover)
        setupViewModel( mangaCover)

    }

    private fun setupViewModel(mangaCover: MangaCover) {
        readerViewModel.getChapterImages(curChapter!!.id)
        readerViewModel.getMangaChapters(mangaCover.id)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                readerViewModel.chapterImages.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.tvReaderMessage.visibility = View.GONE
                        }

                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            // chuyển đổi thành list MangaPage để chạy adapter
                            val images = uiState.data
                            readerAdapter.submitList(images)
                            binding.rcvReader.scrollToPosition(0)
                            if (images.isNotEmpty()) {
                                binding.pageSeekBar.max = images.size - 1
                                binding.pageSeekBar.progress = 0
                            }

                            editView(curChapter!!)
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvReaderMessage.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                readerViewModel.mangaChaptersStateDTO.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                        }

                        is UiState.Success -> {
                            chapters = uiState.data
                            chaptersByLanguage =
                                chapters.filter { it.attributes.translatedLanguage == curChapter!!.attributes.translatedLanguage }
                            readerShowChaptersBottomSheet.chapterCount = chaptersByLanguage.size // lấy số lượng chapter
                            readerShowChaptersBottomSheet.chapters = chaptersByLanguage // lấy danh sách chapter

                            editView(curChapter!!)
                        }

                        is UiState.Error -> {
                        }
                    }
                }
            }
        }
    }

    private fun editView(chapter: MangaChapterDTO) {
        curChapter = chapter

        readerShowChaptersBottomSheet.updateChapterPicked(curChapter!!)

        binding.tvChapterTitle.text =
            "Chapter ${chaptersByLanguage.indexOf(chapter) + 1}"
        binding.tvMangaTitle.text = chapter.attributes.title
        binding.tvPageIndicator.text =
            (chaptersByLanguage.indexOf(chapter) + 1).toString() + " / " + chaptersByLanguage.size

        val currentChapterIndex = chaptersByLanguage.indexOf(chapter)
        if (currentChapterIndex == 0) {
            binding.btnPrevChapter.alpha = 0.5f
            binding.btnPrevChapter.isEnabled = false
        }
        if (currentChapterIndex == chaptersByLanguage.size - 1) {
            binding.btnNextChapter.alpha = 0.5f
            binding.btnNextChapter.isEnabled = false
        }
        binding.btnPrevChapter.setOnClickListener {
            readerViewModel.getChapterImages(chaptersByLanguage[currentChapterIndex - 1].id)
            curChapter = chaptersByLanguage[currentChapterIndex - 1]
        }
        binding.btnNextChapter.setOnClickListener {
            readerViewModel.getChapterImages(chaptersByLanguage[currentChapterIndex + 1].id)
            curChapter = chaptersByLanguage[currentChapterIndex + 1]
        }
    }

    private fun setupView(chapter: MangaChapterDTO, mangaCover: MangaCover) {
        binding.rcvReader.layoutManager = LinearLayoutManager(this.context)
        binding.rcvReader.adapter = readerAdapter

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSetting.setOnClickListener {
            val readerSettingBottomSheet = ReaderSettingBottomSheet()
            readerSettingBottomSheet.show(parentFragmentManager, null)
        }

        binding.btnShowChapters.setOnClickListener {
            readerShowChaptersBottomSheet.onClickChapter = { mangaChapterDTO ->
                readerViewModel.getChapterImages(mangaChapterDTO.id)
            }

            readerShowChaptersBottomSheet.show(parentFragmentManager, null)
        }


        binding.pageSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.rcvReader.scrollToPosition(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        setupReaderBar()
    }

    private fun setupReaderBar() {
        binding.rcvReader.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 12 && isControlVisible) {
                    isControlVisible = false
                    binding.bottomControlBar.fadeOutAnim()
                    binding.topReaderBar.fadeOutAnim()
                } else if (dy < -12 && !isControlVisible) {
                    isControlVisible = true
                    binding.bottomControlBar.fadeInAnim()
                    binding.topReaderBar.fadeInAnim()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}