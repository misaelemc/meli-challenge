package com.mmunoz.meli.search.impl.data.repositories

import com.mmunoz.meli.search.impl.data.api.DEFAULT_LIMIT
import com.mmunoz.meli.search.impl.data.api.SearchApi
import com.mmunoz.meli.search.impl.data.models.SearchResponse
import io.reactivex.Single
import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchApi: SearchApi) {

    fun searchBy(
        page: Int,
        query: String? = null,
        category: String? = null
    ): Single<SearchResponse> {
        val offset = if (page == 0) 0 else page * DEFAULT_LIMIT
        return searchApi.searchBy(offset, query, category)
    }
}