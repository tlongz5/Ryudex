package com.example.RyuDex.ui.views.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.RyuDex.databinding.BottomSheetFilterExploreBinding
import com.example.RyuDex.utils.ChipFactory.createChip
import com.example.RyuDex.utils.Constant
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class FilterExploreBottomSheet: BottomSheetDialogFragment() {
    private var _binding: BottomSheetFilterExploreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        _binding = BottomSheetFilterExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnApply.setOnClickListener {
            dismiss()
        }

        Constant.CONTENT_RATINGS.forEach { contentRating ->
            val chip = createChip(context = requireContext(), text = contentRating.second)
            binding.chipGroupContentRating.addView(chip)
         }

        Constant.STATES.forEach { state ->
            val chip = createChip(context = requireContext(), text = state.second)
            binding.chipGroupState.addView(chip)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}