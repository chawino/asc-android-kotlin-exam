package com.chawin.ascend_android_exam.domain.detail

import com.chawin.ascend_android_exam.base.UseCase
import com.chawin.ascend_android_exam.domain.home.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: DetailRepository
) : UseCase<String, Product>() {

    override suspend fun executeRepo(request: String): Flow<Product> =
        repository.getProduct(request)

    override fun validateRequest(request: String): String = request
}