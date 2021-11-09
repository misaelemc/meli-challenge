package com.mmunoz.base.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mmunoz.base.R

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val textViewTitle by lazy { findViewById<TextView>(R.id.textView_title) }
    private val imageViewIcon by lazy { findViewById<ImageView>(R.id.imageView_icon) }

    init {
        inflate(context, R.layout.error_layout, this)
    }

    fun setData(title: String, @DrawableRes drawable: Int? = null) {
        textViewTitle.text = title
        if (drawable != null) {
            imageViewIcon.setImageDrawable(ContextCompat.getDrawable(context, drawable))
        }
    }
}