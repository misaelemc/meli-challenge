package com.mmunoz.meli.categories.impl.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.databinding.MeliCategoriesImplCategoryViewBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CategoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private lateinit var data: CategoryModel

    private val binding =
        MeliCategoriesImplCategoryViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        isClickable = true
        isFocusable = true
    }

    @ModelProp
    fun setData(data: CategoryModel) {
        this.data = data
    }

    @CallbackProp
    fun setListener(listener: Listener?) {
        setOnClickListener {
            listener?.onCategoryClicked(data)
        }
    }

    @AfterPropsSet
    fun bindData() {
        binding.textViewCategory.text = data.name
    }

    interface Listener {

        fun onCategoryClicked(data: CategoryModel)
    }
}