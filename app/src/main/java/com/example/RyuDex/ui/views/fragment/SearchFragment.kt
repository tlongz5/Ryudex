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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.RyuDex.R
import com.example.RyuDex.databinding.FragmentSearchBinding
import com.example.RyuDex.model.UiState
import com.example.RyuDex.ui.adapter.FeaturedAdapter
import com.example.RyuDex.ui.viewmodel.SearchViewModel
import com.example.RyuDex.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private val featuredAdapter = FeaturedAdapter(
        hotMangas = emptyList(),
        hotTags = Constant.POPULAR_TAGS.toList(),
        onClickManga = {
            // chuyển tới trang chi tiết truyện
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(it))
        },
        onClickTag = {
            // chuyển tới trang danh sách truyện theo tag
            //NOTEEE
        },
        onClickExplore = {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToExploreFragment())
        },
        onClickSeeAll = {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToCategoryFragment())
        }
    )



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        viewModel.getMangaHotList()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.mangaCoversState.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rcvHot.visibility = View.GONE
                        }

                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rcvHot.visibility = View.VISIBLE
                            val hotMangas = uiState.data
                            featuredAdapter.updateHotMangas(hotMangas)
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rcvHot.visibility = View.VISIBLE
                            Toast.makeText(
                                this@SearchFragment.context,
                                uiState.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupView() {
        binding.rcvHot.adapter = featuredAdapter
        binding.rcvHot.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }
}