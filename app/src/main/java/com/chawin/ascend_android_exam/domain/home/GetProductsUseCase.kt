package com.chawin.ascend_android_exam.domain.home

import com.chawin.ascend_android_exam.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: HomeRepository
) : UseCase<Unit, List<Product>>() {

    override suspend fun executeRepo(request: Unit): Flow<List<Product>> =
        repository.getProducts()

    override fun validateRequest(request: Unit) = request
}