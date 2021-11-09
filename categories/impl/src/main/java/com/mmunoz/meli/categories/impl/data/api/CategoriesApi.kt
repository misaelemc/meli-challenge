package com.mmunoz.meli.categories.impl.data.api

import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.data.models.SubCategoryModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriesApi {

    @GET("/sites/MLA/categories")
    fun getCategories(): Single<List<CategoryModel>>

    @GET("/categories/{id}")
    fun getSubCategoriesByCategoryId(@Path("id") id: String): Single<SubCategoryModel>
}