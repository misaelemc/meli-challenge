package com.mmunoz.meli.search.impl.ui.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.mmunoz.meli.search.impl.di.components.inject

class SearchFragment: Fragment() {

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    companion object {

        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }
}