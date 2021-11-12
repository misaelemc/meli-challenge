package com.mmunoz.base.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mmunoz.base.R

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val textViewTitle by lazy { findViewById<TextView>(R.id.textView_title) }

    init {
        inflate(context, R.layout.error_layout, this)
    }

    fun setData(title: String) {
        textViewTitle.text = title
    }
}