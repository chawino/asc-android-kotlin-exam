package com.chawin.ascend_android_exam.data

import com.chawin.ascend_android_exam.data.home.ProductResponseModel
import retrofit2.http.GET

interface DessertService {

    @GET("/products")
    suspend fun getProducts(): List<ProductResponseModel>
}