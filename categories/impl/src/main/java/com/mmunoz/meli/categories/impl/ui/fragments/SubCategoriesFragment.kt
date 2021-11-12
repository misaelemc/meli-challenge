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
import com.mmunoz.base.ui.viewModels.AppViewModel
import com.mmunoz.meli.categories.impl.data.models.SubCategoryItemModel
import com.mmunoz.meli.categories.impl.data.models.SubCategoryModel
import com.mmunoz.meli.categories.impl.databinding.MeliCategoriesImplFragmentBinding
import com.mmunoz.meli.categories.impl.ui.viewModels.SubCategoriesViewModel
import com.mmunoz.meli.categories.impl.ui.views.BannerView
import com.mmunoz.meli.categories.impl.ui.views.SubCategoryView
import com.mmunoz.meli.categories.impl.ui.views.bannerView
import com.mmunoz.meli.categories.impl.ui.views.subCategoryView
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SubCategoriesFragment : Fragment(), SubCategoryView.Listener, BannerView.Listener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SubCategoriesViewModel
    private lateinit var sharedViewModel: AppViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setErrorListener()
        setupViewModel()
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
    }

    private fun setupViewModel() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SubCategoriesViewModel::class.java)
        lifecycle.addObserver(viewModel)
        viewModel.error.observe(viewLifecycleOwner, {
            binding.errorView.setData(getString(it))
            binding.errorView.visibility = View.VISIBLE
        })
        viewModel.data.observe(viewLifecycleOwner, { data ->
            loadData(data)
        })
        viewModel.dataLoading.observe(viewLifecycleOwner, { loading ->
            binding.loaderView.visibility = if (loading) View.VISIBLE else View.GONE
        })
    }

    private fun loadData(data: SubCategoryModel) {
        binding.errorView.visibility = View.GONE
        binding.recyclerView.withModels {
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
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, TWO_COLUMNS, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(false)
    }

    private fun setErrorListener() {
        binding.errorView.setOnRefreshClicked {
            binding.errorView.visibility = View.GONE
            viewModel.onResume()
        }
    }

    companion object {

        const val BANNER_TAG = "banner_tag"
        const val SUB_CATEGORIES_ARGS = "sub_categories_args"
        const val TWO_COLUMNS = 2
    }

}