package com.mmunoz.meli.search.api

import androidx.fragment.app.Fragment

interface SearchFeatureLoader {

    fun getFragment(args: SearchArgs? = null): Fragment
}