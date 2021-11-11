package com.mmunoz.meli.categories.impl.ui.viewModels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.data.repositories.CategoriesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val repository: CategoriesRepository
) : ViewModel(), LifecycleObserver {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _categories = MutableLiveData<List<CategoryModel>>()
    val categories: LiveData<List<CategoryModel>> = _categories

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var disposable: Disposable? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (disposable == null || categories.value == null) {
            disposable = repository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _dataLoading.value = true }
                .doFinally { _dataLoading.value = false }
                .subscribe({
                    _categories.value = it
                }, {
                    _error.value = it.message.orEmpty()
                })
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        disposable?.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        disposable = null
    }
}