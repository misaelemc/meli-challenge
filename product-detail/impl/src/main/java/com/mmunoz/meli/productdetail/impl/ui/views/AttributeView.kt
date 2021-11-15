package com.mmunoz.meli.productdetail.impl.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.mmunoz.meli.productdetail.api.data.models.Attribute
import com.mmunoz.meli.productdetail.impl.databinding.MeliProductDetailImplAttributeLayoutBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class AttributeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private lateinit var data: Attribute

    private val binding = MeliProductDetailImplAttributeLayoutBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    @ModelProp
    fun setData(data: Attribute) {
        this.data = data
    }

    @AfterPropsSet
    fun bindData() {
        binding.textViewKey.text = data.name
        binding.textViewValue.text = data.valueName
    }
}