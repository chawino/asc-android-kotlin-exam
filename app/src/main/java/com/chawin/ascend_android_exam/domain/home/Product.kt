package com.chawin.ascend_android_exam.domain.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String?,
    val title: String?,
    val image: String?,
    val content: String?,
    val isNewProduct: Boolean = false,
    val price: String?,
) : Parcelable