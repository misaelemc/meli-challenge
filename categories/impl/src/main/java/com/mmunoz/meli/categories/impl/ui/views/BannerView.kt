package com.mmunoz.meli.categories.impl.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.bumptech.glide.Glide
import com.mmunoz.meli.categories.impl.R
import com.mmunoz.meli.categories.impl.databinding.MeliCategoriesImplBannerViewBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class BannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private lateinit var pictureImage: String

    private val binding =
        MeliCategoriesImplBannerViewBinding.inflate(LayoutInflater.from(context), this)

    @ModelProp
    fun setData(pictureImage: String) {
        this.pictureImage = pictureImage
    }

    @CallbackProp
    fun setListener(listener: Listener?) {
        binding.textViewBack.setOnClickListener {
            listener?.onBackPressed()
        }
    }

    @AfterPropsSet
    fun bindData() {
        Glide.with(this)
            .load(pictureImage)
            .error(R.drawable.meli_categories_impl_ic_no_photography)
            .into(binding.imageViewBanner)
    }

    interface Listener {

        fun onBackPressed()
    }
}