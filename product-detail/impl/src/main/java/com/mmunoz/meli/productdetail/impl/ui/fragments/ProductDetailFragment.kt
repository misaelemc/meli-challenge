package com.mmunoz.meli.productdetail.impl.ui.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.mmunoz.meli.productdetail.impl.di.components.inject

class ProductDetailFragment: Fragment() {

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    companion object {

        fun newInstance(): ProductDetailFragment {
            return ProductDetailFragment()
        }
    }
}