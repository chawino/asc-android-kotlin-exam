package com.chawin.ascend_android_exam.ui.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeContext(
    val id: String?,
    val title: String?,
    val image: String?,
    val content: String?,
    val isNewProduct: Boolean = false,
    val price: String?,
) : Parcelable