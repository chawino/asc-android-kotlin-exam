package com.chawin.ascend_android_exam.ext

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

fun BigDecimal?.displayAmount2Digit() =
    if (this == null) "" else toString().toAmountFormat2Digit()

fun String?.toAmountFormat2Digit(): String =
    this?.let {
        try {
            decimal2DigitFormatter.format(it.removeComma().toDouble())
        } catch (e: NumberFormatException) {
            ""
        }
    } ?: ""

fun String.removeComma(): String = this.replace(",", "")

private val decimal2DigitFormatter by lazy {
    val formatter = DecimalFormat("#,###,##0.00")
    formatter.roundingMode = RoundingMode.HALF_UP
    return@lazy formatter
}
