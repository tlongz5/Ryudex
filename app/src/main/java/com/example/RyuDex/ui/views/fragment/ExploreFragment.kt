package com.example.RyuDex.ui.views.fragment

import ExploreFeatureAdapter
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.RyuDex.R
import com.example.RyuDex.databinding.FragmentExploreBinding
import com.example.RyuDex.ui.adapter.FilterMangaAdapter
import com.example.RyuDex.ui.viewmodel.ExploreViewModel
import com.example.RyuDex.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private val exploreViewModel: ExploreViewModel by viewModels()

    private val exploreFeatureAdapter = ExploreFeatureAdapter(
        genres = Constant.POPULAR_TAGS.toList().drop(1),
        callbackShowMore = {

        },
        callbackClickTag = { tags ->
            getDataFromQuery(tags)
        }
    )

    private val exploreMangaAdapter = FilterMangaAdapter(
        callbackClickMangaCover = {
            findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToDetailFragment(mangaCover = it))
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        val concatAdapter = ConcatAdapter(
            exploreFeatureAdapter,
            exploreMangaAdapter
        )

        val layoutManager = GridLayoutManager(requireContext(), 3)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 3 else 1
            }
        }

        binding.rcvMain.layoutManager = layoutManager
        binding.rcvMain.adapter = concatAdapter
    }

    private fun setupViewModel() {
        getDataFromQuery(null)
    }

    private fun getDataFromQuery(tags:List<String>?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                exploreViewModel.getMangaListFromQuery(tags).collect{
                    exploreMangaAdapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}