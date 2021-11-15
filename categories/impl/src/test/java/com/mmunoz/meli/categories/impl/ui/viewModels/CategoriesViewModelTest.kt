package com.mmunoz.meli.categories.impl.ui.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mmunoz.base.data.managers.DisposableManager
import com.mmunoz.meli.categories.impl.data.models.CategoryModel
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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CategoriesViewModelTest  {

    @Mock
    lateinit var errorObserver: Observer<Int>

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var categoriesObserver: Observer<List<CategoryModel>>

    @Mock
    lateinit var repository: CategoriesRepository

    @Mock
    lateinit var disposableManager: DisposableManager

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CategoriesViewModel

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = CategoriesViewModel(repository, disposableManager)
        viewModel.error.observeForever(errorObserver)
        viewModel.categories.observeForever(categoriesObserver)
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
    fun `when onResume is called getCategories is executed if there are no categories in liveData`() {
        val category = Mockito.mock(CategoryModel::class.java)
        val categories = listOf(category)
        Mockito.`when`(repository.getCategories())
            .thenReturn(Single.just(categories))

        viewModel.onResume()
        Assert.assertTrue(viewModel.categories.value == categories)
    }

    @Test
    fun `when onResume is called getCategories is executed and should trigger an error`() {
        val exception = Exception("Error")
        Mockito.`when`(repository.getCategories())
            .thenReturn(Single.error(exception))
        viewModel.onResume()
        Mockito.verify(errorObserver, Mockito.times(1)).onChanged(anyInt())
    }

    @Test
    fun `when onResume is called and categories liveData has results getCategories method must not be called`() {
        val category = Mockito.mock(CategoryModel::class.java)
        viewModel.setCategories(listOf(category))
        viewModel.onResume()
        Mockito.verify(repository, Mockito.times(0)).getCategories()
    }

    @Test
    fun `when setCategories method is called categories liveData should be triggered`() {
        val category = Mockito.mock(CategoryModel::class.java)
        val categories = listOf(category)
        viewModel.setCategories(categories)
        Assert.assertTrue(viewModel.categories.value == categories)
    }
}