package com.mmunoz.meli.categories.impl.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubCategoryModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("children_categories") val childrenCategories: List<SubCategoryItemModel>,
    @SerializedName("total_items_in_this_category") val totalItems: Int
) : Parcelable