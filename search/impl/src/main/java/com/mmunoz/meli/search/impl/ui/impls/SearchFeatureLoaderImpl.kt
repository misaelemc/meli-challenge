package com.mmunoz.meli.search.impl.ui.impls

import androidx.fragment.app.Fragment
import com.mmunoz.meli.search.api.SearchFeatureLoader
import com.mmunoz.meli.search.impl.ui.fragments.SearchFragment
import javax.inject.Inject

class SearchFeatureLoaderImpl @Inject constructor() : SearchFeatureLoader {

    override fun getFragment(): Fragment {
        return SearchFragment.newInstance()
    }
}