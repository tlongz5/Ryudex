package com.example.RyuDex.ui.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.RyuDex.R
import com.example.RyuDex.databinding.FragmentDownloadDetailBinding
import com.example.RyuDex.ui.viewmodel.DownloadDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DownloadDetailFragment : Fragment() {
    private var _binding : FragmentDownloadDetailBinding? = null
    val binding get() = _binding!!

    private val viewModel: DownloadDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDownloadDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}