package com.mmunoz.meli.productdetail.impl.ui.viewModels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.mmunoz.meli.productdetail.api.data.models.Product

class ProductDetailViewModel constructor(
    private val productArg: Product
) : ViewModel(), LifecycleObserver {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        _product.value = productArg
    }
}