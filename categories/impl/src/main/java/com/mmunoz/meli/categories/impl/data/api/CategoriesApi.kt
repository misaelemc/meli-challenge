package com.mmunoz.meli.categories.impl.data.api

import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.data.models.SubCategoryModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

const val CATEGORIES_API = "/sites/MLA/categories"
const val SUB_CATEGORIES_API = "/categories/{id}"

interface CategoriesApi {

    @GET(CATEGORIES_API)
    fun getCategories(): Single<List<CategoryModel>>

    @GET(SUB_CATEGORIES_API)
    fun getSubCategoriesByCategoryId(@Path("id") id: String): Single<SubCategoryModel>
}