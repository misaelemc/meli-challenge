package com.mmunoz.meli.search.impl.data.api

import com.mmunoz.meli.search.impl.data.models.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val DEFAULT_LIMIT = 10
const val SEARCH_API = "/sites/MLA/search?"

interface SearchApi {

    @GET(SEARCH_API)
    fun searchBy(
        @Query("offset") offset: Int,
        @Query("q") query: String? = null,
        @Query("category") category: String? = null,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
    ): Single<SearchResponse>

}