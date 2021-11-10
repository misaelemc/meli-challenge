package com.mmunoz.meli.search.impl.ui.impls

import androidx.fragment.app.Fragment
import com.mmunoz.meli.search.api.SearchArgs
import com.mmunoz.meli.search.api.SearchFeatureLoader
import com.mmunoz.meli.search.impl.ui.fragments.SearchBottomSheet
import javax.inject.Inject

class SearchFeatureLoaderImpl @Inject constructor() : SearchFeatureLoader {

    override fun getFragment(args: SearchArgs?): Fragment {
        return SearchBottomSheet.newInstance(args)
    }
}