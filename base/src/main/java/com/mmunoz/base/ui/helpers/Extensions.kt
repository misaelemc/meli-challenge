package com.mmunoz.base.ui.helpers

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.abs

private const val DOT_SYMBOL = '.'
private const val COMMA_SYMBOL = ','
private const val DOLLAR_SYMBOL = "$"
private const val MONEY_FORMAT = "#,###,###,###.##;-#,###,###,###.##"

fun Double.priceToString(): String {
    val maxDecimals = 2
    val minDecimals = 2

    val currencySymbol = DOLLAR_SYMBOL
    val symbols = DecimalFormatSymbols(Locale.US).apply {
        decimalSeparator = COMMA_SYMBOL
        groupingSeparator = DOT_SYMBOL
    }
    val moneyFormat = DecimalFormat(MONEY_FORMAT, symbols).apply {
        maximumFractionDigits = maxDecimals
        minimumFractionDigits = minDecimals
    }

    return "$currencySymbol${moneyFormat.format(abs(this))}"
}