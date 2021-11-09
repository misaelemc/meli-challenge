package com.mmunoz.meli.categories.impl.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mmunoz.meli.categories.impl.R
import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.databinding.MeliCategoriesImplFragmentBinding
import com.mmunoz.meli.categories.impl.ui.views.CategoryView
import com.mmunoz.meli.categories.impl.ui.views.categoryView
import dagger.android.support.AndroidSupportInjection

class CategoriesFragment : Fragment(), CategoryView.Listener {

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
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onCategoryClicked(data: CategoryModel) {
        findNavController().navigate(R.id.categoriesFragment_to_subCategoriesFragment)
    }

    private fun setupRecyclerView() {
        val categoriesList = listOf<CategoryModel>(
            CategoryModel("MLA1403", "Alimentos y Bebidas"),
            CategoryModel("MLA1071", "Animales y Mascotas"),
            CategoryModel("MLA1367", "Antigüedades y Colecciones"),
            CategoryModel("MLA1368", "Arte, Librería y Mercería")
        )
        //binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.withModels {
            categoriesList.map { category ->
                categoryView {
                    id(category.id)
                    data(category)
                    listener(this@CategoriesFragment)
                }
            }
        }
    }

}