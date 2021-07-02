package com.chawin.ascend_android_exam.data

import android.os.Parcelable
import com.chawin.ascend_android_exam.domain.home.Product
import com.chawin.ascend_android_exam.ui.home.HomeInfo
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

fun ProductResponseModel.mapToDomain(): Product =
    Product(
        id, title, image, content, isNewProduct, price
    )