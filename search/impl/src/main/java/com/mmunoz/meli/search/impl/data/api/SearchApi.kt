package com.mmunoz.meli.search.impl.data.api

import com.mmunoz.meli.search.impl.data.models.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val DEFAULT_LIMIT = 10

interface SearchApi {

    @GET("/sites/MLA/search?")
    fun searchBy(
        @Query("offset") offset: Int,
        @Query("q") query: String? = null,
        @Query("category") category: String? = null,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
    ): Single<SearchResponse>

}