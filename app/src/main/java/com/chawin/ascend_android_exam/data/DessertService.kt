package com.chawin.ascend_android_exam.data

import retrofit2.http.GET
import retrofit2.http.Path

interface DessertService {

    @GET("/products")
    suspend fun getProducts(): List<ProductResponseModel>

    @GET("/products/{productId}")
    suspend fun getProduct(@Path("productId") productId: String): ProductResponseModel
}