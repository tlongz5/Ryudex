package com.example.RyuDex.ui.views.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.RyuDex.databinding.BottomSheetReaderSettingBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReaderSettingBottomSheet: BottomSheetDialogFragment() {
    private var _binding : BottomSheetReaderSettingBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetReaderSettingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCloseSettings.setOnClickListener {
            dismiss()
        }
        binding.btnVertical.setOnClickListener {

        }
        binding.btnHorizontal.setOnClickListener {

        }
        binding.icBrightnessLow.setOnClickListener {
            // minus
        }
        binding.icBrightnessHigh.setOnClickListener {

        }
        binding.themeLight.setOnClickListener {

        }
        binding.themeSepia.setOnClickListener {

        }
        binding.themeDark.setOnClickListener {

        }

    }

    private fun updateReadingModeState() {
        val selectedBg = Color.parseColor("#222222")
        val normalBg = Color.parseColor("#FFFFFF")
        val selectedText = Color.parseColor("#FFFFFF")
        val normalText = Color.parseColor("#222222")
        val normalStroke = Color.parseColor("#D8D8D8")

        val verticalSelected = binding.btnVertical.isChecked
        val horizontalSelected = binding.btnHorizontal.isChecked

        binding.btnVertical.backgroundTintList = ColorStateList.valueOf(
            if (verticalSelected) selectedBg else normalBg
        )
        binding.btnVertical.setTextColor(
            if (verticalSelected) selectedText else normalText
        )
        binding.btnVertical.iconTint = ColorStateList.valueOf(
            if (verticalSelected) selectedText else normalText
        )
        binding.btnVertical.strokeColor = ColorStateList.valueOf(
            if (verticalSelected) selectedBg else normalStroke
        )

        binding.btnHorizontal.backgroundTintList = ColorStateList.valueOf(
            if (horizontalSelected) selectedBg else normalBg
        )
        binding.btnHorizontal.setTextColor(
            if (horizontalSelected) selectedText else normalText
        )
        binding.btnHorizontal.iconTint = ColorStateList.valueOf(
            if (horizontalSelected) selectedText else normalText
        )
        binding.btnHorizontal.strokeColor = ColorStateList.valueOf(
            if (horizontalSelected) selectedBg else normalStroke
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}