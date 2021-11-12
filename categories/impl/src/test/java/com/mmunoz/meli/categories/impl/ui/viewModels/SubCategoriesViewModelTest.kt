package com.mmunoz.meli.categories.impl.ui.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mmunoz.base.data.managers.DisposableManager
import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.data.models.SubCategoryModel
import com.mmunoz.meli.categories.impl.data.repositories.CategoriesRepository
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SubCategoriesViewModelTest {

    @Mock
    lateinit var errorObserver: Observer<Int>

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var subCategoryObserver: Observer<SubCategoryModel>

    @Mock
    lateinit var repository: CategoriesRepository

    @Mock
    lateinit var disposableManager: DisposableManager

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private val categoryModel = CategoryModel("MLA1512", "Agro")
    private lateinit var viewModel: SubCategoriesViewModel

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = SubCategoriesViewModel(categoryModel, repository, disposableManager)
        viewModel.error.observeForever(errorObserver)
        viewModel.data.observeForever(subCategoryObserver)
        viewModel.dataLoading.observeForever(loadingObserver)
    }

    @Test
    fun `Verify that the onPause method clear the RX disposables`() {
        viewModel.onPause()
        Mockito.verify(disposableManager, Mockito.times(1)).dispose()
    }

    @Test
    fun `Verify that the onCleared() method clear the composite reference`() {
        val method = viewModel::class.java.getDeclaredMethod("onCleared")
        method.invoke(viewModel)
        Mockito.verify(disposableManager, Mockito.times(1)).clear()
    }

    @Test
    fun `when onResume is called getSubCategoriesByCategoryId is executed if there are no values in liveData`() {
        val data = Mockito.mock(SubCategoryModel::class.java)
        Mockito.`when`(repository.getSubCategoriesByCategoryId(categoryModel.id))
            .thenReturn(Single.just(data))

        viewModel.onResume()
        Assert.assertTrue(viewModel.data.value == data)
    }

    @Test
    fun `when onResume is called getSubCategoriesByCategoryId is executed and should trigger an error`() {
        val exception = Exception("Error")
        Mockito.`when`(repository.getSubCategoriesByCategoryId(categoryModel.id))
            .thenReturn(Single.error(exception))
        viewModel.onResume()
        Mockito.verify(errorObserver, Mockito.times(1))
            .onChanged(ArgumentMatchers.anyInt())
    }

    @Test
    fun `when onResume is called and data liveData has results getSubCategoriesByCategoryId method must not be called`() {
        val data = Mockito.mock(SubCategoryModel::class.java)
        viewModel.setSubCategoryData(data)
        viewModel.onResume()
        Mockito.verify(repository, Mockito.times(0)).getCategories()
    }

    @Test
    fun `when setSubCategoryData method is called data liveData should be triggered`() {
        val data = Mockito.mock(SubCategoryModel::class.java)
        viewModel.setSubCategoryData(data)
        Assert.assertTrue(viewModel.data.value == data)
    }
}