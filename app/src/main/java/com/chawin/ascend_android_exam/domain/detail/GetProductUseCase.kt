package com.chawin.ascend_android_exam.domain.detail

import com.chawin.ascend_android_exam.base.MISSING_REQUIRE
import com.chawin.ascend_android_exam.base.ServiceException
import com.chawin.ascend_android_exam.base.UseCase
import com.chawin.ascend_android_exam.domain.home.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: DetailRepository
) : UseCase<String, Product>() {

    override suspend fun executeRepo(request: String): Flow<Product> =
        repository.getProduct(request)

    override fun validateRequest(request: String): String {
        if (request.isBlank()) {
            throw ServiceException(MISSING_REQUIRE, PRODUCT_ID_IS_EMPTY)
        } else {
            return request
        }
    }

    companion object {
        private const val PRODUCT_ID_IS_EMPTY = "PRODUCT_ID_IS_EMPTY"
    }
}