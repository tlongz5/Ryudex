package com.example.RyuDex.ui.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.RyuDex.databinding.DialogDownloadMangaBinding

class DialogDownloadManga: DialogFragment() {
    private var _binding: DialogDownloadMangaBinding? = null
    private val binding get() = _binding!!

    var onClickDownload: ((Int, Boolean) -> Unit)? = null
    var chapters: Int? = null
    var chaptersByLanguage: Int? = null
    var queryByLanguage: Boolean = false
    var mangaName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogDownloadMangaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnStartDownload.setOnClickListener {
            val chaptersToDownload = when(binding.rgDownloadOptions.checkedRadioButtonId) {
                binding.rbWholeManga.id -> chapters
                binding.rbTranslatedChapters.id -> {
                    queryByLanguage = true
                    chaptersByLanguage
                }
                else -> {
                    queryByLanguage = true
                    val customChapters = binding.tvDropdownCount.text.toString()
                    val count = customChapters.split(" ")[3].toInt()
                    count
                }
            }

            onClickDownload?.invoke(chaptersToDownload!!,queryByLanguage)
            dismiss()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}