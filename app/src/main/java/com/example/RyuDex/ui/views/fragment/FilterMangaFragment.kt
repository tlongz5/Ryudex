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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.RyuDex.R
import com.example.RyuDex.databinding.FragmentFilterMangaBinding
import com.example.RyuDex.model.UiState
import com.example.RyuDex.ui.adapter.FilterMangaAdapter
import com.example.RyuDex.ui.viewmodel.FilterMangaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterMangaFragment : Fragment() {
    private var _binding: FragmentFilterMangaBinding? = null
    val binding get() = _binding!!

    private val viewModel: FilterMangaViewModel by viewModels()

    private val args: FilterMangaFragmentArgs by navArgs()

    private val filterMangaAdapter = FilterMangaAdapter(
        callbackClickMangaCover = {
            findNavController().navigate(FilterMangaFragmentDirections.actionFilterMangaFragmentToDetailFragment(it))
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMangaList(
                    title = args.titleRelated,
                    authors = args.authorId?.let { listOf(it) },
                    includedTags = args.tagId?.let { listOf(it) }
                ).collectLatest { pagingData ->
                    filterMangaAdapter.submitData(pagingData)
                }
            }
        }

        filterMangaAdapter.addLoadStateListener { uiState ->
            when (uiState.refresh) {
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rcvManga.visibility = View.GONE
                }

                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rcvManga.visibility = View.VISIBLE
                }

                is LoadState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rcvManga.visibility = View.VISIBLE

                    Toast.makeText(
                        requireContext(),
                        "Load failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupView() {
        binding.tvTitle.text = args.titleRelated ?: args.authorId ?: args.tagId ?: ""
        binding.rcvManga.adapter = filterMangaAdapter
        binding.rcvManga.layoutManager = GridLayoutManager(this.context, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}