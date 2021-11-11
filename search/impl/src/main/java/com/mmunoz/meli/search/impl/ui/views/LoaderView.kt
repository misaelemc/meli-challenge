package com.mmunoz.meli.search.impl.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.ModelView
import com.mmunoz.meli.search.impl.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class LoaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.loader_layout, this)
        findViewById<ProgressBar>(R.id.progress_circular).apply {
            indeterminateDrawable.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.primary_dark_color
                )
            )
        }
    }
}
