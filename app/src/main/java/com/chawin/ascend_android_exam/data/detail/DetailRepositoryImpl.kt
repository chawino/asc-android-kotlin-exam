package com.chawin.ascend_android_exam.data.home

import com.chawin.ascend_android_exam.base.BaseService
import com.chawin.ascend_android_exam.data.DessertService
import com.chawin.ascend_android_exam.data.ProductResponseModel
import com.chawin.ascend_android_exam.data.mapToDomain
import com.chawin.ascend_android_exam.domain.detail.DetailRepository
import com.chawin.ascend_android_exam.domain.home.Product
import com.chawin.ascend_android_exam.util.Network
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DetailRepositoryImpl @Inject constructor(
    private val service: DessertService,
    private val network: Network
) : DetailRepository {
    override fun getProduct(request: String): Flow<Product> =
        object : BaseService<ProductResponseModel,
                Product>(network) {
            override suspend fun callApi(): ProductResponseModel = service.getProduct(request)

            override fun mapper(from: ProductResponseModel): Product = from.mapToDomain()
        }.execute()
}