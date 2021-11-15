package com.mmunoz.meli.categories.impl.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.test.espresso.idling.CountingIdlingResource
import com.mmunoz.base.decrementWithoutErrors
import com.mmunoz.base.ui.viewModels.AppViewModel
import com.mmunoz.meli.categories.impl.data.models.SubCategoryItemModel
import com.mmunoz.meli.categories.impl.data.models.SubCategoryModel
import com.mmunoz.meli.categories.impl.databinding.MeliCategoriesImplSubCategoriesFragmentBinding
import com.mmunoz.meli.categories.impl.ui.viewModels.SubCategoriesViewModel
import com.mmunoz.meli.categories.impl.ui.views.BannerView
import com.mmunoz.meli.categories.impl.ui.views.SubCategoryView
import com.mmunoz.meli.categories.impl.ui.views.bannerView
import com.mmunoz.meli.categories.impl.ui.views.subCategoryView
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SubCategoriesFragment : Fragment(), SubCategoryView.Listener, BannerView.Listener {

    @Inject
    lateinit var idLingResource: CountingIdlingResource

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SubCategoriesViewModel
    private lateinit var sharedViewModel: AppViewModel

    private var _binding: MeliCategoriesImplSubCategoriesFragmentBinding? = null
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
        _binding = MeliCategoriesImplSubCategoriesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setErrorListener()
        setupViewModel()
        binding.subCategoryErrorView.setOnRefreshClicked {
            idLingResource.increment()
            viewModel.onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        idLingResource.increment()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }

    override fun onSubCategoryClicked(data: SubCategoryItemModel) {
        sharedViewModel.setAction(AppViewModel.Actions.OnSubCategorySelected(data.id, data.name))
        idLingResource.decrementWithoutErrors()
    }

    private fun setupViewModel() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SubCategoriesViewModel::class.java)
        lifecycle.addObserver(viewModel)
        viewModel.error.observe(viewLifecycleOwner, {
            binding.subCategoryErrorView.setData(getString(it))
            idLingResource.decrementWithoutErrors()
        })
        viewModel.data.observe(viewLifecycleOwner, { data ->
            loadData(data)
            idLingResource.decrementWithoutErrors()
        })
        viewModel.dataLoading.observe(viewLifecycleOwner, { show ->
            binding.loaderView.loading(show)
        })
    }

    private fun loadData(data: SubCategoryModel) {
        binding.subCategoryErrorView.hide()
        binding.recyclerViewSubCategories.withModels {
            bannerView {
                id(BANNER_TAG)
                data(data.picture)
                listener(this@SubCategoriesFragment)
            }
            data.childrenCategories.map { category ->
                subCategoryView {
                    id(category.id)
                    data(category)
                    spanSizeOverride { _, _, _ -> 1 }
                    listener(this@SubCategoriesFragment)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSubCategories.layoutManager =
            GridLayoutManager(context, TWO_COLUMNS, GridLayoutManager.VERTICAL, false)
        binding.recyclerViewSubCategories.setHasFixedSize(false)
    }

    private fun setErrorListener() {
        binding.subCategoryErrorView.setOnRefreshClicked {
            idLingResource.increment()
            viewModel.onResume()
        }
    }

    companion object {

        const val BANNER_TAG = "banner_tag"
        const val SUB_CATEGORIES_ARGS = "sub_categories_args"
        const val TWO_COLUMNS = 2
    }

}