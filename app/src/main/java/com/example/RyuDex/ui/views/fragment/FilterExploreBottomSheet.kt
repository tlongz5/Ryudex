package com.example.RyuDex.ui.views.fragment

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.RyuDex.databinding.BottomSheetFilterExploreBinding
import com.example.RyuDex.model.dto.manga.TagItemDTO
import com.example.RyuDex.utils.ChipFactory.createChip
import com.example.RyuDex.utils.Constant
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class FilterExploreBottomSheet: BottomSheetDialogFragment() {
    private var _binding: BottomSheetFilterExploreBinding? = null
    private val binding get() = _binding!!

    var selectedTags: List<String> = emptyList()
    var tags: List<TagItemDTO> = emptyList()

    var languages:String? = null
    var contentRatings:String? = null
    var states:String? = null

    //callback language, contentRating,state, tags
    var onFilter: ((String?,String?,String?,List<String>) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetFilterExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnApply.setOnClickListener {
            onFilter?.invoke(languages,contentRatings,states,selectedTags)
            dismiss()
        }

        Constant.CONTENT_RATINGS.forEach { contentRating ->
            val chip = createChip(context = requireContext(), text = contentRating.second)
            chip.tag= contentRating.first
            chip.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    contentRatings = contentRating.first
                }
            }
            binding.chipGroupContentRating.addView(chip)
         }

        Constant.STATES.forEach { state ->
            val chip = createChip(context = requireContext(), text = state.second)
            chip.tag = state.first
            chip.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    states = state.first
                }
            }
            binding.chipGroupState.addView(chip)
        }

        tags.forEach { tag ->
            val chip = createChip(context = requireContext(), text = tag.attributes?.name?.get("en")!!)
            chip.tag=tag.id
            if(selectedTags.contains(tag.id)) chip.isChecked = true
            chip.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    selectedTags + tag.id
                }else selectedTags - tag.id
            }
            binding.chipGroupGenres.addView(chip)
        }

        val language = Constant.CODE_TO_LANGUAGE.values.toList()

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_dropdown_item_1line,
            language
        )

        binding.autoLanguage.setAdapter(adapter)

        binding.autoLanguage.setOnItemClickListener { parent, _, position, _ ->
            val selectedName = parent.getItemAtPosition(position).toString()
            languages = Constant.LANGUAGE_TO_CODE[selectedName]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}