package com.mmunoz.meli.search.impl.ui.adapters

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.mmunoz.meli.productdetail.api.data.models.Product
import com.mmunoz.meli.search.impl.ui.views.LoaderViewModel_
import com.mmunoz.meli.search.impl.ui.views.LoadingViewModel_
import com.mmunoz.meli.search.impl.ui.views.ProductView
import com.mmunoz.meli.search.impl.ui.views.productView

class SearchAdapter constructor(
    private val listener: ProductView.Listener
) : AsyncEpoxyController() {

    private var hasMorePages: Boolean = true

    var data: List<Product> = listOf()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        data.forEach { product ->
            productView {
                id(product.id)
                data(product)
                listener(listener)
            }
        }
        LoadingViewModel_()
            .id("loader")
            .addIf(data.isEmpty(), this)
        getPagingLoader().addIf(data.isNotEmpty() && hasMorePages, this)
    }

    fun dispatch(data: List<Product>) {
        this.data = data
    }

    fun append(data: List<Product>, hasMorePages: Boolean) {
        this.hasMorePages = hasMorePages
        this.data = ArrayList(this.data).apply {
            addAll(data)
        }
    }

    private fun getPagingLoader(): EpoxyModel<*> {
        return LoaderViewModel_()
            .id("pagination_loader")
    }
}