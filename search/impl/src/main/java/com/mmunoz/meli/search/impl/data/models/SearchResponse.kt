package com.mmunoz.meli.search.impl.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mmunoz.meli.productdetail.api.data.models.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse(
    @SerializedName("query") val name: String,
    @SerializedName("results") val results: List<Product>,
): Parcelable