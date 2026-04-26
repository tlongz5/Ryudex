package com.example.RyuDex.ui.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.RyuDex.databinding.FragmentReaderBinding
import com.example.RyuDex.model.UiState
import com.example.RyuDex.ui.adapter.ReaderAdapter
import com.example.RyuDex.ui.viewmodel.ReaderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReaderFragment : Fragment() {
    private var _binding: FragmentReaderBinding? = null
    val binding get() = _binding!!

    private val safeArgs : ReaderFragmentArgs by navArgs()

    private val readerViewModel : ReaderViewModel by viewModels()

    private var readerAdapter = ReaderAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chapterId = safeArgs.chapterId
        val mangaId = safeArgs.mangaId

        setupView(chapterId,mangaId)
        setupViewModel(chapterId,mangaId)

    }

    private fun setupViewModel(chapterId: String, mangaId: String) {
        readerViewModel.getChapterImages(chapterId)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                readerViewModel.chapterImages.collect{ uiState ->
                    when(uiState){
                        is UiState.Loading ->{
                            binding.progressBar.visibility = View.VISIBLE
                        }is UiState.Success ->{
                            binding.progressBar.visibility = View.GONE
                            // chuyển đổi thành list MangaPage để chạy adapter
                            val images = uiState.data
                            readerAdapter.submitList(images)
                        }is UiState.Error ->{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@ReaderFragment.context,uiState.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupView(chapterId: String, mangaId: String) {
        binding.rcvReader.layoutManager = LinearLayoutManager(this.context)
        binding.rcvReader.adapter = readerAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}