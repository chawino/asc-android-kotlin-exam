package com.chawin.ascend_android_exam.data.home

import android.util.Log
import com.chawin.ascend_android_exam.base.BaseService
import com.chawin.ascend_android_exam.data.DessertService
import com.chawin.ascend_android_exam.data.ProductResponseModel
import com.chawin.ascend_android_exam.domain.home.Product
import com.chawin.ascend_android_exam.domain.home.HomeRepository
import com.chawin.ascend_android_exam.util.Network
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeRepositoryImpl @Inject constructor(
    private val service: DessertService,
    private val network: Network
) : HomeRepository {
    override fun getProducts(): Flow<List<Product>> =
        object : BaseService<List<ProductResponseModel>,
                List<Product>>(network) {
            override suspend fun callApi(): List<ProductResponseModel> = service.getProducts()

            override fun mapper(from: List<ProductResponseModel>): List<Product> = from.map {
                Log.d("TGA","mapper : " + it.title)
                Product(
                    id = it.id,
                    title = it.title,
                    image = it.image,
                    content = it.content,
                    isNewProduct = it.isNewProduct,
                    price = it.price
                )
            }
        }.execute()
}