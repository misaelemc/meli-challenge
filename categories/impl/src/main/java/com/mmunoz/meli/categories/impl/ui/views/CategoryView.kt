package com.mmunoz.meli.categories.impl.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.google.android.material.card.MaterialCardView
import com.mmunoz.meli.categories.impl.R
import com.mmunoz.meli.categories.impl.data.models.CategoryModel

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CategoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private lateinit var data: CategoryModel

    private val textViewCategory: TextView by lazy {
        findViewById(R.id.textView_category)
    }

    private val cardViewLayout: MaterialCardView by lazy {
        findViewById(R.id.cardView)
    }

    init {
        inflate(context, R.layout.meli_categories_impl_category_view, this)
    }

    @ModelProp
    fun setData(data: CategoryModel) {
        this.data = data
    }

    @CallbackProp
    fun setListener(listener: Listener?) {
        cardViewLayout.setOnClickListener {
            listener?.onCategoryClicked(data)
        }
    }

    @AfterPropsSet
    fun bindData() {
        textViewCategory.text = data.name
    }

    interface Listener {

        fun onCategoryClicked(data: CategoryModel)
    }
}