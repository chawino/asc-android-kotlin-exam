package com.chawin.ascend_android_exam.domain.home

import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getProducts(): Flow<List<Product>>
}