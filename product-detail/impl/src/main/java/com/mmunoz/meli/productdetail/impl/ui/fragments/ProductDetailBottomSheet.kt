package com.mmunoz.meli.productdetail.impl.ui.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmunoz.base.ui.helpers.priceToString
import com.mmunoz.meli.productdetail.api.data.models.Address
import com.mmunoz.meli.productdetail.api.data.models.Attribute
import com.mmunoz.meli.productdetail.api.data.models.Product
import com.mmunoz.meli.productdetail.impl.R
import com.mmunoz.meli.productdetail.impl.databinding.MeliProductDetailImplBottomSheetBinding
import com.mmunoz.meli.productdetail.impl.di.components.inject
import com.mmunoz.meli.productdetail.impl.ui.viewModels.ProductDetailViewModel
import com.mmunoz.meli.productdetail.impl.ui.views.attributeView
import java.util.Locale
import javax.inject.Inject

class ProductDetailBottomSheet : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ProductDetailViewModel

    private var _binding: MeliProductDetailImplBottomSheetBinding? = null
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
        _binding = MeliProductDetailImplBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        binding.imageViewButtonBack.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setCancelable(false)
        dialog.setOnShowListener {
            setDialogExpanded(dialog)
            setStateChangeListener(dialog)
        }
        return dialog
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductDetailViewModel::class.java)
        lifecycle.addObserver(viewModel)
        viewModel.product.observe(viewLifecycleOwner, { product ->
            bindProductDetailHeader(product)
        })
    }

    private fun bindProductDetailHeader(product: Product) {
        binding.textViewConditions.text = getString(
            R.string.meli_product_detail_impl_conditions,
            product.condition.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            },
            product.soldQuantity.toString()
        )
        binding.textViewTitle.text = product.title
        binding.textViewPrice.text = product.price.priceToString()
        binding.textViewSoldBy.text = Html.fromHtml(
            getString(R.string.meli_product_detail_impl_sold_by, product.domainId),
            Html.FROM_HTML_MODE_COMPACT
        )
        bindAddress(product.address)
        Glide.with(this)
            .load(product.thumbnail)
            .into(binding.imageViewPhoto)
        addAttributes(product.attributes)
    }

    private fun bindAddress(address: Address?) {
        address?.let {
            binding.textViewLocationValue.text = getString(
                R.string.meli_product_detail_impl_location_value,
                it.cityName,
                it.stateName
            )
        } ?: kotlin.run {
            binding.textViewLocationValue.visibility = GONE
        }
    }

    private fun addAttributes(attributes: List<Attribute>) {
        binding.recyclerView.withModels {
            attributes
                .filter { it.name.isNotBlank() && !it.valueName.isNullOrBlank() }
                .forEach { attribute ->
                    attributeView {
                        id(attribute.id)
                        data(attribute)
                    }
                }
        }
    }

    private fun setDialogExpanded(dialog: BottomSheetDialog) {
        dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.let {
            BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
            val layoutParams = it.layoutParams
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            it.layoutParams = layoutParams
        }
    }

    private fun setStateChangeListener(dialog: BottomSheetDialog) {
        BottomSheetBehavior.from(dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet) as FrameLayout)
            .addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dialog.cancel()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
    }

    companion object {

        const val PRODUCT_DETAIL_ARGS = "product_detail_args"

        fun newInstance(product: Product): ProductDetailBottomSheet {
            return ProductDetailBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(PRODUCT_DETAIL_ARGS, product)
                }
            }
        }
    }
}