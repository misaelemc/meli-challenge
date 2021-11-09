package com.mmunoz.meli.categories.impl.ui.impls

import androidx.fragment.app.Fragment
import com.mmunoz.meli.categories.api.CategoriesFeatureLoader
import com.mmunoz.meli.categories.impl.ui.fragments.CategoriesNavigationFragment
import javax.inject.Inject

class CategoriesFeatureLoaderImpl @Inject constructor() : CategoriesFeatureLoader {

    override fun getFragment(): Fragment {
        return CategoriesNavigationFragment.newInstance()
    }
}