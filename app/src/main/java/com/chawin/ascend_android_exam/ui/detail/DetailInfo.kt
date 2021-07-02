package com.chawin.ascend_android_exam.ui.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailInfo(
    val id: String?,
    val title: String?,
    val image: String?,
    val content: String?,
    val isNewProduct: Boolean = false,
    val price: String?,
) : Parcelable