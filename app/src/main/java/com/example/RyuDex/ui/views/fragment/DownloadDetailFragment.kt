package com.example.RyuDex.ui.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.RyuDex.R
import com.example.RyuDex.databinding.FragmentDownloadDetailBinding
import com.example.RyuDex.model.UiState
import com.example.RyuDex.ui.adapter.DownloadDetailAdapter
import com.example.RyuDex.ui.viewmodel.DownloadDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DownloadDetailFragment : Fragment() {
    private var _binding : FragmentDownloadDetailBinding? = null
    val binding get() = _binding!!

    private val viewModel: DownloadDetailViewModel by viewModels()

    private val adapter = DownloadDetailAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDownloadDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()

    }

    private fun setupView() {
        binding.rvQueuedDownloads.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel.getAllDownloadingManga()
        viewModel.getMangaCovers()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.workInfosState.collectLatest { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {

                        }is UiState.Success -> {
                            val workInfos = uiState.data
                            val mangaCovers = workInfos.map { workInfo ->

                            }
                        }is UiState.Error -> {
                            Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.mangaCoversState.collectLatest { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {

                        }

                        is UiState.Success -> {
                            adapter.submitList(uiState.data)
                        }
                        is UiState.Error -> {
                            Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
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