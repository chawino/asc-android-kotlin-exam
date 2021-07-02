package com.chawin.ascend_android_exam.domain.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(
    val id: String?,
    val title: String?,
    val image: String?,
    val content: String?,
    val isNewProduct: Boolean = false,
    val price: String?,
) : Parcelable