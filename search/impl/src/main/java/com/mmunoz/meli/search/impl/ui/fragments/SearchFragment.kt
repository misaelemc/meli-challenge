package com.mmunoz.meli.search.impl.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmunoz.base.IN_ALPHA
import com.mmunoz.base.OUT_ALPHA
import com.mmunoz.base.TestIdlingResource
import com.mmunoz.base.ui.viewModels.AppViewModel
import com.mmunoz.meli.productdetail.api.ProductDetailFeatureLoader
import com.mmunoz.meli.productdetail.api.data.models.Product
import com.mmunoz.meli.search.impl.databinding.MeliSearchImplFragmentBinding
import com.mmunoz.meli.search.impl.di.components.inject
import com.mmunoz.meli.search.impl.ui.adapters.SearchAdapter
import com.mmunoz.meli.search.impl.ui.viewModels.SearchViewModel
import com.mmunoz.meli.search.impl.ui.views.ProductView
import javax.inject.Inject

class SearchFragment : Fragment(), TextWatcher, ProductView.Listener {

    @Inject
    lateinit var productDetailFeatureLoader: ProductDetailFeatureLoader

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SearchViewModel
    private lateinit var sharedViewModel: AppViewModel

    private lateinit var searchAdapter: SearchAdapter

    private var _binding: MeliSearchImplFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MeliSearchImplFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setFocusListener()
        searchByKeyboard()
        setErrorListener()
        setupRecyclerView()
        addDeleteButtonListener()
        binding.editTextSearch.addTextChangedListener(this)
    }

    override fun onDestroyView() {
        binding.recyclerViewSearch.removeOnScrollListener(paginationScrollListener())
        binding.editTextSearch.removeTextChangedListener(this)
        _binding = null
        super.onDestroyView()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (s?.isNotEmpty() == true) {
            binding.imageButtonDelete.animate().alpha(IN_ALPHA)
            binding.imageButtonAction.animate().alpha(OUT_ALPHA)
        } else {
            binding.imageButtonDelete.animate().alpha(OUT_ALPHA)
            binding.imageButtonAction.animate().alpha(IN_ALPHA)
        }
    }

    override fun onProductClicked(data: Product) {
        productDetailFeatureLoader.showBottomSheet(requireActivity() as AppCompatActivity, data)
    }

    private fun setFocusListener() {
        binding.editTextSearch.setOnFocusChangeListener { _, _ ->
            if (binding.editTextSearch.text.isNotEmpty()) {
                binding.imageButtonAction.animate().alpha(OUT_ALPHA)
                binding.imageButtonDelete.animate().alpha(IN_ALPHA)
            } else {
                binding.imageButtonDelete.animate().alpha(OUT_ALPHA)
                binding.imageButtonAction.animate().alpha(IN_ALPHA)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SearchViewModel::class.java)
        lifecycle.addObserver(viewModel)
        sharedViewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        listenToObservers()
    }

    private fun listenToObservers() {
        sharedViewModel.data.observe(viewLifecycleOwner, { action ->
            if (action is AppViewModel.Actions.OnSubCategorySelected) {
                viewModel.onSearchByQuery(categoryId = action.id)
                TestIdlingResource.increment()
            } else if (action is AppViewModel.Actions.ClearSearch) {
                clearSearch()
            }
        })
        viewModel.error.observe(viewLifecycleOwner, {
            binding.searchErrorView.setData(getString(it))
            TestIdlingResource.decrement()
        })
        viewModel.products.observe(viewLifecycleOwner, { data ->
            binding.searchErrorView.hide()
            if (data.firstPage) {
                searchAdapter.dispatch(data.products)
            } else {
                searchAdapter.append(data.products, data.hasMorePages)
            }
            TestIdlingResource.decrement()
        })
    }

    private fun searchByKeyboard() {
        binding.editTextSearch.setOnEditorActionListener(
            TextView.OnEditorActionListener { view, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.onSearchByQuery(binding.editTextSearch.text.toString())
                    TestIdlingResource.increment()
                    view.hideKeyboard()
                    return@OnEditorActionListener true
                }
                false
            }
        )
    }

    private fun addDeleteButtonListener() {
        binding.imageButtonDelete.setOnClickListener {
            binding.editTextSearch.setText(CLEAR_TEXT)
            binding.imageButtonDelete.animate().alpha(OUT_ALPHA)
            if (viewModel.categoryId == null) {
                clearSearch()
            } else {
                viewModel.onSearchByQuery(forceSearch = true)
                TestIdlingResource.increment()
            }
            it.hideKeyboard()
        }
    }

    private fun clearSearch() {
        viewModel.reset()
        binding.searchErrorView.hide()
        searchAdapter.dispatch(emptyList())
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter(this)
        binding.recyclerViewSearch.layoutManager =
            LinearLayoutManager(context, GridLayoutManager.VERTICAL, false)
        binding.recyclerViewSearch.setHasFixedSize(false)
        binding.recyclerViewSearch.addOnScrollListener(paginationScrollListener())
        binding.recyclerViewSearch.setController(searchAdapter)
        searchAdapter.dispatch(emptyList())
    }

    private fun paginationScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (viewModel.dataLoading.value == false) {
                with(recyclerView.layoutManager as LinearLayoutManager) {
                    viewModel.listScrolled(
                        this.childCount,
                        this.findLastVisibleItemPosition(),
                        this.itemCount
                    )
                }
            }
        }
    }

    private fun setErrorListener() {
        binding.searchErrorView.setOnRefreshClicked {
            viewModel.onSearchByQuery(viewModel.currentQuery, viewModel.categoryId, true)
            TestIdlingResource.increment()
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    companion object {
        private const val CLEAR_TEXT = ""

        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }
}