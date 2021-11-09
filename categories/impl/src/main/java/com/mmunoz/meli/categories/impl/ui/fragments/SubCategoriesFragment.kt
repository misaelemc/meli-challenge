package com.mmunoz.meli.categories.impl.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mmunoz.meli.categories.impl.data.models.SubCategoryModel
import com.mmunoz.meli.categories.impl.databinding.MeliCategoriesImplFragmentBinding
import com.mmunoz.meli.categories.impl.ui.views.subCategoryView
import dagger.android.support.AndroidSupportInjection

class SubCategoriesFragment: Fragment() {

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

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(false)

        val items = listOf<SubCategoryModel>(
            SubCategoryModel("MLA1403", "Alimentos y Bebidas", 50),
            SubCategoryModel("MLA1071", "Animales y Mascotas", 70),
            SubCategoryModel("MLA1367", "Antigüedades y Colecciones", 130),
            SubCategoryModel("MLA1368", "Arte, Librería y Mercería", 1000)
        )

        binding.recyclerView.withModels {
            items.map { item ->
                subCategoryView {
                    id(item.id)
                    data(item)
                    spanSizeOverride { _, _, _ -> 1 }
                }
            }
        }
    }

    companion object {
        const val THREE_COLUMNS = 3
    }
}