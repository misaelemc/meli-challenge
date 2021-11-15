package com.mmunoz.meli.categories.impl.data.repositories

import com.mmunoz.meli.categories.impl.data.api.CategoriesApi
import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.data.models.SubCategoryModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CategoriesRepositoryTest {

    @Mock
    lateinit var api: CategoriesApi

    @Mock
    lateinit var categoryModel: CategoryModel

    @Mock
    lateinit var subCategoryModel: SubCategoryModel

    private lateinit var categoriesRepository: CategoriesRepository

    private var categoryId = "MLA1055"

    @Before
    fun setUp() {
        categoriesRepository = CategoriesRepository(api)
    }

    @Test
    fun `Getting categories should return a list of CategoryModel when there are categories available`() {
        Mockito.`when`(api.getCategories()).thenReturn(Single.just(listOf(categoryModel)))
        categoriesRepository.getCategories()
            .test()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `Getting categories should return an exception when something happens in back`() {
        val exception = Exception("Mock Error")
        Mockito.`when`(api.getCategories()).thenReturn(Single.error(exception))
        categoriesRepository.getCategories()
            .test()
            .assertError(exception)
    }

    @Test
    fun `Getting sub categories by categoryId should return SubCategoryModel when there is data`() {
        Mockito.`when`(api.getSubCategoriesByCategoryId(categoryId))
            .thenReturn(Single.just(subCategoryModel))
        categoriesRepository.getSubCategoriesByCategoryId(categoryId)
            .test()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `Getting sub categories by categoryId should return an exception when something happens in back`() {
        val exception = Exception("Mock Error")
        Mockito.`when`(api.getSubCategoriesByCategoryId(categoryId))
            .thenReturn(Single.error(exception))
        categoriesRepository.getSubCategoriesByCategoryId(categoryId)
            .test()
            .assertError(exception)
            .dispose()
    }
}