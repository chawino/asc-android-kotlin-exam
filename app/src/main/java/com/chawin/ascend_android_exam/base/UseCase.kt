package com.chawin.ascend_android_exam.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow

abstract class UseCase<Q, R> {

    abstract fun validateRequest(request: Q): Q

    protected abstract suspend fun executeRepo(request: Q, isRetry: Boolean): Flow<R>

    fun execute(request: Q, isRetry: Boolean = true): Flow<R> =
        flow { emit(validateRequest(request)) }
            .flatMapConcat { executeRepo(it, isRetry) }
}
