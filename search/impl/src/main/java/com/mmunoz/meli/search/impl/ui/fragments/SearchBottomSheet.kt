package com.mmunoz.meli.search.impl.ui.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmunoz.meli.search.api.SearchArgs
import com.mmunoz.meli.search.impl.R
import com.mmunoz.meli.search.impl.databinding.MeliSearchImplBottomSheetBinding
import com.mmunoz.meli.search.impl.di.components.inject

class SearchBottomSheet : BottomSheetDialogFragment(), TextWatcher {

    private var _binding: MeliSearchImplBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MeliSearchImplBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchByKeyboard()
        addDeleteButtonListener()
        binding.editTextSearch.addTextChangedListener(this)
    }

    override fun onDestroyView() {
        binding.editTextSearch.removeTextChangedListener(this)
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setCancelable(false)
        dialog.setOnShowListener {
            setDialogExpanded(dialog)
            setStateChangeListener(dialog)
        }
        return dialog
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun dismiss() = dismissAllowingStateLoss()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        //textListener?.invoke(s.toString())
        if (s?.isNotEmpty() == true) {
            binding.imageButtonDelete.animate().alpha(IN_ALPHA)
        } else {
            binding.imageButtonDelete.animate().alpha(OUT_ALPHA)
        }
    }

    private fun searchByKeyboard() {
        binding.editTextSearch.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //searchByKeyboard?.invoke(binding.rdsSearchEditText.text.toString())
                    return@OnEditorActionListener true
                }
                false
            }
        )
    }

    private fun addDeleteButtonListener() {
        binding.imageButtonDelete.setOnClickListener {
            binding.editTextSearch.setText(CLEAR_TEXT)
            binding.imageButtonDelete.animate().alpha(OUT_ALPHA)
        }
    }

    private fun setDialogExpanded(dialog: BottomSheetDialog) {
        dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.let {
            BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setStateChangeListener(dialog: BottomSheetDialog) {
        BottomSheetBehavior.from(dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet) as FrameLayout)
            .addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dialog.cancel()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
    }

    companion object {

        private const val IN_ALPHA = 1F
        private const val OUT_ALPHA = 0F
        private const val CLEAR_TEXT = ""
        private const val SEARCH_ARGS = "search_args"

        fun newInstance(args: SearchArgs?): SearchBottomSheet {
            return SearchBottomSheet().apply {
                if (args != null) {
                    arguments = Bundle().apply {
                        putParcelable(SEARCH_ARGS, args)
                    }
                }
            }
        }
    }
}