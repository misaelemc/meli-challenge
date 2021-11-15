package com.mmunoz.meli.search.impl.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.bumptech.glide.Glide
import com.mmunoz.base.ui.helpers.priceToString
import com.mmunoz.meli.productdetail.api.data.models.Product
import com.mmunoz.meli.search.impl.R
import com.mmunoz.meli.search.impl.databinding.MeliSearchImplProductViewBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ProductView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = MeliSearchImplProductViewBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    private lateinit var data: Product

    @ModelProp
    fun setData(data: Product) {
        this.data = data
    }

    @CallbackProp
    fun setListener(listener: Listener?) {
        binding.cardView.setOnClickListener {
            listener?.onProductClicked(data)
        }
    }

    @AfterPropsSet
    fun bindData() {
        binding.textViewName.text = data.title
        binding.textViewPrice.text = data.price.priceToString()
        if (data.shipping.freeShipping) {
            binding.textViewShipping.text =
                context.getString(R.string.meli_search_impl_free_shipping)
        } else {
            binding.textViewShipping.visibility = GONE
        }
        Glide.with(this)
            .load(data.thumbnail)
            .into(binding.imageViewProduct)
    }

    interface Listener {

        fun onProductClicked(data: Product)
    }
}