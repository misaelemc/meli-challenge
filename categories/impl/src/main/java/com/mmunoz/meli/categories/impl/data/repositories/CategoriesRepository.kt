package com.mmunoz.meli.categories.impl.data.repositories

import com.mmunoz.meli.categories.impl.data.api.CategoriesApi
import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.data.models.SubCategoryModel
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
class CategoriesRepository @Inject constructor(private val api: CategoriesApi) {

    fun getCategories(): Single<List<CategoryModel>> {
        return api.getCategories()
    }

    fun getSubCategoriesByCategoryId(id: String): Single<SubCategoryModel> {
        return api.getSubCategoriesByCategoryId(id)
    }
}