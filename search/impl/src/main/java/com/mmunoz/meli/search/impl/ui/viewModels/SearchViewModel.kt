package com.mmunoz.meli.search.impl.ui.viewModels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.mmunoz.base.data.managers.DisposableManager
import com.mmunoz.base.data.models.getErrorMessage
import com.mmunoz.meli.search.impl.data.models.SearchData
import com.mmunoz.meli.search.impl.data.models.SearchResponse
import com.mmunoz.meli.search.impl.data.repositories.SearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel constructor(
    private val repository: SearchRepository,
    private val disposableManager: DisposableManager
) : ViewModel(), LifecycleObserver {

    var categoryId: String? = null
    var currentQuery: String? = null
    var hasMorePages = true
    var lastRequestPage = 0

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _products = MutableLiveData<SearchData>()
    val products: LiveData<SearchData> = _products

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        disposableManager.dispose()
    }

    fun onSearchByQuery(
        query: String? = null,
        categoryId: String? = null,
        forceSearch: Boolean = false
    ) {
        if (currentQuery != query || forceSearch || categoryId != null) {
            reset()
            currentQuery = query
            this.categoryId = categoryId
            _products.value = SearchData(emptyList(), hasMorePages = true, firstPage = true)
            repository.searchBy(lastRequestPage, query, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _dataLoading.value = true }
                .doFinally { _dataLoading.value = false }
                .subscribe({ response ->
                    setupPage(response)
                    _products.value = SearchData(response.results, hasMorePages, true)
                }, this::onFailure)
                .let(disposableManager::add)
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount && hasMorePages) {
            paginate()
        }
    }

    fun reset() {
        hasMorePages = true
        lastRequestPage = 0
        currentQuery = null
        categoryId = null
    }

    private fun paginate() {
        repository.searchBy(lastRequestPage, currentQuery, categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _dataLoading.value = true }
            .doFinally { _dataLoading.value = false }
            .subscribe(this::onSuccess, this::onFailure)
            .let(disposableManager::add)
    }

    private fun onSuccess(response: SearchResponse) {
        setupPage(response)
        _products.value = SearchData(response.results, hasMorePages, false)
    }

    private fun onFailure(throwable: Throwable) {
        _error.value = throwable.getErrorMessage()
    }

    private fun setupPage(response: SearchResponse) {
        hasMorePages = if (response.results.isNotEmpty()) {
            lastRequestPage++
            true
        } else {
            false
        }
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 0
    }
}