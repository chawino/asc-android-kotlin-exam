package com.chawin.ascend_android_exam.domain.detail

import com.chawin.ascend_android_exam.domain.home.Product
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    fun getProduct(productId: String): Flow<Product>
}