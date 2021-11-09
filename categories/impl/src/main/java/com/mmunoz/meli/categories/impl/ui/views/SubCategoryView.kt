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
import com.mmunoz.meli.categories.impl.data.models.SubCategoryModel

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SubCategoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private lateinit var data: SubCategoryModel

    private val textViewCategory: TextView by lazy {
        findViewById(R.id.textView_category)
    }

    private val textViewTotalItems: TextView by lazy {
        findViewById(R.id.textView_total_items)
    }

    private val cardViewLayout: MaterialCardView by lazy {
        findViewById(R.id.cardView)
    }

    init {
        inflate(context, R.layout.meli_categories_impl_sub_category_view, this)
    }

    @ModelProp
    fun setData(data: SubCategoryModel) {
        this.data = data
    }

    @CallbackProp
    fun setListener(listener: Listener?) {
        cardViewLayout.setOnClickListener {
            listener?.onSubCategoryClicked(data)
        }
    }

    @AfterPropsSet
    fun bindData() {
        textViewCategory.text = data.name
        textViewTotalItems.text = resources.getString(
            R.string.meli_categories_impl_total_items,
            data.totalItems.toString()
        )
    }

    interface Listener {

        fun onSubCategoryClicked(data: SubCategoryModel)
    }
}