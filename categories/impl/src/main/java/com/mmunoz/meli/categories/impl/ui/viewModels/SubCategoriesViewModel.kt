package com.mmunoz.meli.categories.impl.ui.viewModels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.mmunoz.base.data.models.getErrorMessage
import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.data.models.SubCategoryModel
import com.mmunoz.meli.categories.impl.data.repositories.CategoriesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SubCategoriesViewModel constructor(
    private val categoryModel: CategoryModel,
    private val repository: CategoriesRepository
) : ViewModel(), LifecycleObserver {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _data = MutableLiveData<SubCategoryModel>()
    val data: LiveData<SubCategoryModel> = _data

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private var disposable: Disposable? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (disposable == null || data.value == null) {
            disposable = repository.getSubCategoriesByCategoryId(categoryModel.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _dataLoading.value = true }
                .doFinally { _dataLoading.value = false }
                .subscribe({
                    _data.value = it
                }, {
                    _error.value = it.getErrorMessage()
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