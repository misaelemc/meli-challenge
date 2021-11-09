package com.mmunoz.meli.categories.impl.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.bumptech.glide.Glide
import com.mmunoz.meli.categories.impl.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class BannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private lateinit var pictureImage: String

    private val imageViewBanner: AppCompatImageView by lazy {
        findViewById(R.id.imageView_banner)
    }

    init {
        inflate(context, R.layout.meli_categories_impl_banner_view, this)
    }

    @ModelProp
    fun setData(pictureImage: String) {
        this.pictureImage = pictureImage
    }

    @AfterPropsSet
    fun bindData() {
        Glide.with(this)
            .load(pictureImage)
            .error(R.drawable.meli_categories_impl_ic_no_photography)
            .into(imageViewBanner)
    }
}