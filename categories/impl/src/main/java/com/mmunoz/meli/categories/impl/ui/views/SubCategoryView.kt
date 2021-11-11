package com.mmunoz.meli.categories.impl.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.mmunoz.meli.categories.impl.R
import com.mmunoz.meli.categories.impl.data.models.SubCategoryItemModel
import com.mmunoz.meli.categories.impl.databinding.MeliCategoriesImplSubCategoryViewBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SubCategoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private lateinit var data: SubCategoryItemModel

    private val binding =
        MeliCategoriesImplSubCategoryViewBinding.inflate(LayoutInflater.from(context), this)

    @ModelProp
    fun setData(data: SubCategoryItemModel) {
        this.data = data
    }

    @CallbackProp
    fun setListener(listener: Listener?) {
        binding.cardView.setOnClickListener {
            listener?.onSubCategoryClicked(data)
        }
    }

    @AfterPropsSet
    fun bindData() {
        binding.textViewCategory.text = data.name
        binding.textViewTotalItems.text = resources.getString(
            R.string.meli_categories_impl_total_items,
            data.totalItems.toString()
        )
    }

    interface Listener {

        fun onSubCategoryClicked(data: SubCategoryItemModel)
    }
}