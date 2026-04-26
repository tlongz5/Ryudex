package com.example.RyuDex.ui.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.RyuDex.R
import com.example.RyuDex.databinding.FragmentHomeBinding
import com.example.RyuDex.model.UiState
import com.example.RyuDex.ui.adapter.HomeBannerAdapter
import com.example.RyuDex.ui.adapter.HomeBannerItemAdapter
import com.example.RyuDex.ui.adapter.HomeMangaListAdapter
import com.example.RyuDex.ui.adapter.HomeRecommendAdapter
import com.example.RyuDex.ui.adapter.HomeTitleAdapter
import com.example.RyuDex.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val homeViewModel : HomeViewModel by viewModels()

    private val titleRecommend = HomeTitleAdapter("Recommend")
    private val titleNewest = HomeTitleAdapter("Latest")
    private val recommendMangaAdapter = HomeRecommendAdapter(
        callbackClickTab = { tag ->
            homeViewModel.getMangaShortCoverList(tag)
        },
        callbackClickMangaCover = { mangaCover ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(mangaCover))
        }
    )
    private val latestMangaAdapter = HomeMangaListAdapter{ mangaCover ->
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(mangaCover))
    }

    private val bannerAdapter = HomeBannerAdapter(
        onClickBanner = { mangaCover ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(mangaCover))
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val concatAdapter = ConcatAdapter(
            bannerAdapter,
            titleRecommend,
            recommendMangaAdapter,
            titleNewest,
            latestMangaAdapter
        )

        binding.rcvMain.adapter=concatAdapter
        binding.rcvMain.layoutManager = LinearLayoutManager(this.context)

        setupViewmodel()
    }

    private fun setupViewmodel() {
        homeViewModel.getMangaShortCoverList(null)
        homeViewModel.getMangaBannerList()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                homeViewModel.getMangaCoverList.collectLatest { pagingData ->
                    latestMangaAdapter.submitData(pagingData)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.mangaCoverShortList.collect { uiState ->
                when(uiState){
                    is UiState.Loading ->{

                    }is UiState.Success ->{
                        recommendMangaAdapter.updateData(uiState.data)
                    }is UiState.Error ->{

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.mangaBannerList.collect { uiState ->
                when(uiState){
                    is UiState.Loading ->{

                    }is UiState.Success ->{
                    bannerAdapter.updateList(uiState.data)
                }is UiState.Error ->{

                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}