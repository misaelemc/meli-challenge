package com.mmunoz.base.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.RelativeLayout
import com.mmunoz.base.R

class LoaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    init {
        inflate(context, R.layout.loader_layout, this)
        gravity = Gravity.CENTER
    }
}