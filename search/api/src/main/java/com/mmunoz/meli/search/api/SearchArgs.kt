package com.mmunoz.meli.search.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchArgs(
    val categoryId: String
): Parcelable