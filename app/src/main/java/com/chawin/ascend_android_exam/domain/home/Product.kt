package com.chawin.ascend_android_exam.domain.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Product(
    val id: String?,
    val title: String?,
    val image: String?,
    val content: String?,
    val isNewProduct: Boolean = false,
    val price: String?,
) : Parcelable