package com.mmunoz.base.ui.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AppViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var dataObserver: Observer<AppViewModel.Actions>

    private lateinit var viewModel: AppViewModel

    @Before
    fun setUp() {
        viewModel = AppViewModel()
        viewModel.data.observeForever(dataObserver)
    }

    @Test
    fun `Verify that ClearSearch action being triggered when setAction is called`() {
        viewModel.setAction(AppViewModel.Actions.ClearSearch)
        Mockito.verify(dataObserver, Mockito.times(1))
            .onChanged(any())
    }

    @Test
    fun `Verify that OnSubCategorySelected action being triggered when setAction is called`() {
        viewModel.setAction(AppViewModel.Actions.OnSubCategorySelected("M312938", "Agro"))
        Mockito.verify(dataObserver, Mockito.times(1))
            .onChanged(any())
    }
}