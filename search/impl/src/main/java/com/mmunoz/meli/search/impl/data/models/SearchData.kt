package com.mmunoz.meli.search.impl.data.models

import com.mmunoz.meli.productdetail.api.data.models.Product

data class SearchData(val products: List<Product>, val hasMorePages: Boolean, val firstPage: Boolean)