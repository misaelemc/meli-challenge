package com.mmunoz.meli.categories.impl.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.test.espresso.idling.CountingIdlingResource
import com.mmunoz.base.decrementWithoutErrors
import com.mmunoz.meli.categories.impl.R
import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.databinding.MeliCategoriesImplFragmentBinding
import com.mmunoz.meli.categories.impl.ui.fragments.SubCategoriesFragment.Companion.SUB_CATEGORIES_ARGS
import com.mmunoz.meli.categories.impl.ui.viewModels.CategoriesViewModel
import com.mmunoz.meli.categories.impl.ui.views.CategoryView
import com.mmunoz.meli.categories.impl.ui.views.categoryView
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CategoriesFragment : Fragment(), CategoryView.Listener {

    @Inject
    lateinit var idLingResource: CountingIdlingResource

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CategoriesViewModel

    private var _binding: MeliCategoriesImplFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MeliCategoriesImplFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        idLingResource.increment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        binding.categoryErrorView.setOnRefreshClicked {
            idLingResource.increment()
            viewModel.onResume()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onCategoryClicked(data: CategoryModel) {
        findNavController().navigate(
            R.id.categoriesFragment_to_subCategoriesFragment,
            Bundle().apply {
                putParcelable(SUB_CATEGORIES_ARGS, data)
            })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CategoriesViewModel::class.java)
        lifecycle.addObserver(viewModel)
        viewModel.error.observe(viewLifecycleOwner, {
            binding.categoryErrorView.setData(getString(it))
            idLingResource.decrementWithoutErrors()
        })
        viewModel.categories.observe(viewLifecycleOwner, { categories ->
            binding.categoryErrorView.hide()
            binding.recyclerViewCategories.withModels {
                categories.map { category ->
                    categoryView {
                        id(category.id)
                        data(category)
                        listener(this@CategoriesFragment)
                    }
                }
            }
            idLingResource.decrementWithoutErrors()
        })
        viewModel.dataLoading.observe(viewLifecycleOwner, { show ->
            binding.loaderView.loading(show)
        })
    }
}