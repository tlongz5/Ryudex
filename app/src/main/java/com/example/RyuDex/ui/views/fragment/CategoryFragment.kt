package com.example.RyuDex.ui.views.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.RyuDex.R
import com.example.RyuDex.databinding.FragmentCategoryBinding
import com.example.RyuDex.ui.adapter.MangaCategoryAdapter
import com.example.RyuDex.ui.adapter.TagCategoryAdapter
import com.example.RyuDex.ui.viewmodel.CategoryViewModel
import com.example.RyuDex.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryViewModel by viewModels()
    private val args: CategoryFragmentArgs by navArgs()

    // lưu giá trị tagId để load manga khi sort
    private var tagId:String? = null
    private var filter:String = "Popular"

    private val mangaAdapter = MangaCategoryAdapter{ mangaCover ->
        findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToDetailFragment(mangaCover))
    }

    // khi click tag, load manga theo tag
    private lateinit var tagAdapter: TagCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tagId = args.tagId
        setupView()
        setupViewModel()
    }

    private fun setupViewModel() {
        getMangaListFromQuery(tagId,filter)
    }

    private fun getMangaListFromQuery(tagId:String?, filter:String) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.getMangaListFromTag(tagId,filter).collectLatest{ pagingData ->
                    mangaAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setupView(){
        tagAdapter = TagCategoryAdapter(
            defaultTag = tagId,
            tags = Constant.POPULAR_TAGS.toList(),
            onClickTag = { tagId ->
                this.tagId = tagId
                getMangaListFromQuery(tagId,filter)
            }
        )

        binding.rcvTags.adapter = tagAdapter
        binding.rcvTags.layoutManager = LinearLayoutManager(this.context)
        binding.rcvMangaByTag.layoutManager = GridLayoutManager(this.context, 2)
        binding.rcvMangaByTag.adapter = mangaAdapter

        binding.btnFilter.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.menu.add("Popular")
            popup.menu.add("Newest")
            popup.menu.add("Recently Added")

            popup.setOnMenuItemClickListener { item ->
                filter = item.title.toString()
                binding.tvSort.text = filter + "▼"
                getMangaListFromQuery(tagId,filter)
                true
            }

            popup.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}