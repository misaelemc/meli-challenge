package com.mmunoz.base.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mmunoz.base.R

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val buttonRefresh by lazy { findViewById<Button>(R.id.button_refresh) }
    private val textViewTitle by lazy { findViewById<TextView>(R.id.textView_title) }

    init {
        inflate(context, R.layout.error_layout, this)
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
    }

    fun setData(title: String) {
        textViewTitle.text = title
    }

    fun setOnRefreshClicked(action: () -> Unit) {
        buttonRefresh.setOnClickListener { action.invoke() }
    }
}