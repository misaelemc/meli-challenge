package com.mmunoz.meli.search.impl.data.repositories

import com.mmunoz.meli.search.impl.data.api.SearchApi
import com.mmunoz.meli.search.impl.data.models.SearchResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchRepositoryTest  {

    @Mock
    lateinit var api: SearchApi

    @Mock
    lateinit var searchResponse: SearchResponse

    private lateinit var searchRepository: SearchRepository

    private val offset = 0
    private val query = "PS5"
    private var categoryId = "MLA1055"

    @Before
    fun setUp() {
        searchRepository = SearchRepository(api)
    }

    @Test
    fun `Searching by query should returns a properly data when there are results available`() {
        Mockito.`when`(api.searchBy(offset, query, null))
            .thenReturn(Single.just(searchResponse))
        searchRepository.searchBy(offset, query, null)
            .test()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `Searching by categoryId should returns a properly data when there are results available`() {
        Mockito.`when`(api.searchBy(offset, null, categoryId))
            .thenReturn(Single.just(searchResponse))
        searchRepository.searchBy(offset, null, categoryId)
            .test()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `Searching by query should return an exception when something happens in back`() {
        val exception = Exception("Mock data error")
        Mockito.`when`(api.searchBy(offset, query, null))
            .thenReturn(Single.error(exception))
        searchRepository.searchBy(offset, query, null)
            .test()
            .assertError(exception)
            .dispose()
    }

    @Test
    fun `Searching by categoryId should return an exception when something happens in back`() {
        val exception = Exception("Mock data error")
        Mockito.`when`(api.searchBy(offset, null, categoryId))
            .thenReturn(Single.error(exception))
        searchRepository.searchBy(offset, null, categoryId)
            .test()
            .assertError(exception)
            .dispose()
    }
}