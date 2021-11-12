package com.mmunoz.meli.search.impl.ui.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mmunoz.meli.productdetail.api.data.models.Product
import com.mmunoz.meli.productdetail.api.data.models.Shipping
import com.mmunoz.meli.search.impl.data.models.SearchData
import com.mmunoz.meli.search.impl.data.models.SearchResponse
import com.mmunoz.meli.search.impl.data.repositories.SearchRepository
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @Mock
    lateinit var errorObserver: Observer<Int>

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var productObserver: Observer<SearchData>

    @Mock
    lateinit var searchRepository: SearchRepository

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    private val query = "PS5"
    private val exception = Exception("Mock data error")
    private val product = Product(
        id = "MLA929389582",
        title = "Nokia 24 M 64 Gb Carbón 3 Gb Ram",
        price = 20999.0,
        address = null,
        thumbnail = "http://http2.mlstatic.com/D_715512-MLA46711394228_072021-I.jpg",
        condition = "new",
        domainId = "domainId",
        shipping = Shipping(freeShipping = false, storePickUp = true),
        attributes = emptyList(),
        soldQuantity = 1000,
        availableQuantity = 300,
        sellerReputation = null
    )
    private val searchResponse: SearchResponse = SearchResponse("", listOf(product))
    private lateinit var currentClass: Class<out SearchViewModel>

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = SearchViewModel(searchRepository)
        currentClass = viewModel::class.java

        viewModel.error.observeForever(errorObserver)
        viewModel.products.observeForever(productObserver)
        viewModel.dataLoading.observeForever(loadingObserver)
    }

    @Test
    fun `Verify that the reset method reset the pagination`() {
        viewModel.reset()
        Assert.assertEquals(viewModel.hasMorePages, true)
        Assert.assertEquals(viewModel.categoryId, null)
        Assert.assertEquals(viewModel.currentQuery, null)
        Assert.assertEquals(viewModel.lastRequestPage, 0)
    }

    @Test
    fun `Verify that the search by query method retrieves data`() {
        Mockito.`when`(searchRepository.searchBy(0, query, null))
            .thenReturn(Single.just(searchResponse))

        Assert.assertEquals(viewModel.lastRequestPage, 0)
        viewModel.onSearchByQuery(query, null, false)
        Assert.assertTrue(viewModel.products.value is SearchData)
        Assert.assertEquals(viewModel.hasMorePages, true)
        Assert.assertEquals(viewModel.lastRequestPage, 1)
    }

    @Test
    fun `Verify that the search by query method retrieves an exception`() {
        Mockito.`when`(searchRepository.searchBy(0, query, null))
            .thenReturn(Single.error(exception))

        Assert.assertEquals(viewModel.hasMorePages, true)
        Assert.assertEquals(viewModel.lastRequestPage, 0)
        viewModel.onSearchByQuery(query, null, false)
        Mockito.verify(errorObserver, Mockito.times(1))
            .onChanged(anyInt())
    }

    @Test
    fun `Verify that the listScrolled method call the paginate method`() {
        lisScrolledMock(1,9,3)
    }

    @Test
    fun `Verify that the listScrolled method does not invoke the paginate method`() {
        lisScrolledMock(0,5,2)
    }

    private fun lisScrolledMock(times: Int, visibleItemCount: Int, lastVisibleItemPosition: Int) {
        Assert.assertEquals(viewModel.hasMorePages, true)
        Assert.assertEquals(viewModel.lastRequestPage, 0)

        Mockito.`when`(
            searchRepository.searchBy(0, null, null)
        ).thenReturn(Single.just(searchResponse))
        viewModel.listScrolled(visibleItemCount, lastVisibleItemPosition, 10)
        Mockito.verify(searchRepository, Mockito.times(times))
            .searchBy(
                eq(0),
                eq(null),
                eq(null)
            )
    }
}