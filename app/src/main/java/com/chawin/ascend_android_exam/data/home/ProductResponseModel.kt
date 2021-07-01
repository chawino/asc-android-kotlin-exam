package com.chawin.ascend_android_exam.data.home

import android.os.Parcelable
import com.chawin.ascend_android_exam.domain.home.Product
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResponseModel(
    @SerializedName("id") @Expose val id: String?,
    @SerializedName("title") @Expose  val title: String?,
    @SerializedName("image") @Expose  val image: String?,
    @SerializedName("content") @Expose  val content: String?,
    @SerializedName("isNewProduct") @Expose  val isNewProduct: Boolean = false,
    @SerializedName("price") @Expose val price: String?,
) : Parcelable