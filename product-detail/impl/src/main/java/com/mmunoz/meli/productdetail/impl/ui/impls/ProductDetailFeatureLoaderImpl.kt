package com.mmunoz.meli.productdetail.impl.ui.impls

import androidx.fragment.app.Fragment
import com.mmunoz.meli.productdetail.api.ProductDetailFeatureLoader
import com.mmunoz.meli.productdetail.impl.ui.fragments.ProductDetailFragment
import javax.inject.Inject

class ProductDetailFeatureLoaderImpl @Inject constructor() : ProductDetailFeatureLoader {

    override fun getFragment(): Fragment {
        return ProductDetailFragment.newInstance()
    }
}