package com.example.RyuDex.ui.views.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import android.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import com.example.RyuDex.databinding.DialogDownloadMangaBinding

class DialogDownloadManga: DialogFragment() {
    private var _binding: DialogDownloadMangaBinding? = null
    private val binding get() = _binding!!

    var onClickDownload: ((Int, Boolean) -> Unit)? = null
    var chapters: Int? = null
    var chaptersByLanguage: Int? = null
    var queryByLanguage: Boolean = false
    var mangaLanguage = ""
    var mangaName = ""

    private lateinit var listPopupWindow: ListPopupWindow

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogDownloadMangaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDropdownCount()

        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.rbCustomChapters.setOnClickListener {
            if (::listPopupWindow.isInitialized) {
                listPopupWindow.show()
            }
        }

        binding.tvAllChapters.text = "$chapters chapters"
        binding.tvTranslatedChapters.text = "$chaptersByLanguage chapters\n$mangaLanguage"
        binding.tvMangaName.text = mangaName

        binding.btnStartDownload.setOnClickListener {
            val chaptersToDownload = when(binding.rgDownloadOptions.checkedRadioButtonId) {
                binding.rbWholeManga.id -> chapters
                binding.rbTranslatedChapters.id -> {
                    queryByLanguage = true
                    chaptersByLanguage
                }
                else -> {
                    queryByLanguage = true
                    val customChapters = binding.rbCustomChapters.text.toString()
                    Log.d("TAG", "customChapters: $customChapters")
                    val count = customChapters.split(" ")[1].toInt()
                    Log.d("TAG", "count: $count")
                    count
                }
            }

            onClickDownload?.invoke(chaptersToDownload!!,queryByLanguage)
            dismiss()
        }

    }

    private fun setupDropdownCount() {
        val maxChapters = chaptersByLanguage ?: 0
        if (maxChapters <= 0) return

        val chapterOptions = mutableListOf<String>()

        val milestones = listOf(5, 10, 20, 30, 50, 100, 200, 500, 1000, 2000)

        for (i in milestones) {
            if (i < maxChapters) {
                chapterOptions.add("$i chapters")
            } else {
                break
            }
        }

        chapterOptions.add("$maxChapters chapters")
        val finalOptions = chapterOptions.distinct()

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            finalOptions
        )


        listPopupWindow = ListPopupWindow(requireContext()).apply {
            setAdapter(adapter)
            anchorView = binding.rbCustomChapters
            setOnItemClickListener { _,_,position, _ ->
                val selectedOption = finalOptions[position]
                binding.rbCustomChapters.text = "First $selectedOption\n$mangaLanguage"
                binding.rbCustomChapters.isChecked = true
                dismiss()
            }

        }



        if (finalOptions.isNotEmpty()) {
            binding.rbCustomChapters.text = "First ${finalOptions[0]}\n$mangaLanguage"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}