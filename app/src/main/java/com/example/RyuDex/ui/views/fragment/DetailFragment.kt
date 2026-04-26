package com.example.RyuDex.ui.views.fragment

import ChaptersAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.RyuDex.R
import com.example.RyuDex.databinding.FragmentDetailBinding
import com.example.RyuDex.model.MangaChapter
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.UiState
import com.example.RyuDex.ui.adapter.RelatedMangaAdapter
import com.example.RyuDex.ui.adapter.TagAdapter
import com.example.RyuDex.ui.viewmodel.DetailViewModel
import com.example.RyuDex.utils.toMangaCover
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    val binding get() = _binding!!

    private val detailViewModel : DetailViewModel by viewModels()

    private val safeArgs : DetailFragmentArgs by navArgs()

    private val relatedMangaAdapter = RelatedMangaAdapter{
        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentSelf(it))
    }
    private val chaptersAdapter = ChaptersAdapter{
        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToReaderFragment(
            chapterId = it.id,
            mangaId = safeArgs.mangaCover.id
        ))
    }

    private val tagAdapter = TagAdapter {

    }

    private var allChapters = listOf<MangaChapter>()
    private var firstChapterByLanguage = allChapters.firstOrNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mangaCover = safeArgs.mangaCover
        setupView(mangaCover)
        setupViewModel(mangaCover)
    }

    private fun setupView(mangaCover: MangaCover) {
        binding.rcvChapter.layoutManager = LinearLayoutManager(this.context)
        binding.rcvChapter.adapter = chaptersAdapter
        binding.rcvRelatedManga.adapter = relatedMangaAdapter
        binding.rcvRelatedManga.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)

        binding.rcvType.adapter = tagAdapter
        tagAdapter.submitList(mangaCover.category)

        binding.tvTitle.text = mangaCover.title
        binding.tvAuthor.text = mangaCover.author.second
        binding.tvDescription.text = mangaCover.description
        binding.btnRead.setOnClickListener {
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToReaderFragment(
                chapterId = firstChapterByLanguage!!.id,
                mangaId = mangaCover.id
            ))
        }

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_language,
            mangaCover.availableLanguages
        )
        binding.languageSpinner.adapter = adapter
        binding.languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val chaptersByLanguage = allChapters.filter { it.attributes.translatedLanguage == mangaCover.availableLanguages[position] }
                firstChapterByLanguage = chaptersByLanguage.firstOrNull()
                chaptersAdapter.submitList(chaptersByLanguage)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.tvMore.setOnClickListener {
            if (binding.tvDescription.maxLines == 5) {
                binding.tvDescription.maxLines = Int.MAX_VALUE
                binding.tvMore.text = "Less"
            } else {
                binding.tvDescription.maxLines = 5
                binding.tvMore.text = "More"
            }
        }

        Glide.with(this)
            .load(mangaCover.img)
            .placeholder(R.drawable.img_bgr)
            .transform(CenterCrop(), BlurTransformation(20))
            .into(binding.imgBanner)

        Glide.with(this)
            .load(mangaCover.img)
            .transform(CenterCrop())
            .into(binding.imgCover)
    }

    private fun setupViewModel(mangaCover: MangaCover) {
        detailViewModel.getMangaChapters(mangaCover.id)
        detailViewModel.getRelatedManga(mangaCover.category.shuffled().mapNotNull { it.first }.take(2))

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                detailViewModel.mangaChaptersState.collect{ uiState ->
                    when(uiState){
                        is UiState.Loading ->{

                        }is UiState.Success ->{
                            val chapters = uiState.data
                            allChapters = chapters
                            val chaptersByLanguage = allChapters.filter { it.attributes.translatedLanguage == mangaCover.availableLanguages[0] }
                            firstChapterByLanguage = chaptersByLanguage.firstOrNull()
                            chaptersAdapter.submitList(chaptersByLanguage)
                            binding.languageSpinner.setSelection(0)
                        }is UiState.Error ->{

                        }

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                detailViewModel.relatedMangaState.collect{ uiState ->
                    when(uiState){
                        is UiState.Loading ->{

                        }is UiState.Success ->{
                            val relatedMangas = uiState.data.map { mangaItem -> mangaItem.toMangaCover() }
                            relatedMangaAdapter.submitList(relatedMangas)

                        }is UiState.Error ->{
                            Toast.makeText(this@DetailFragment.context,uiState.message,Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}