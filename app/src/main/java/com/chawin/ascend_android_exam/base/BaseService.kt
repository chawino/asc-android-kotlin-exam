package com.chawin.ascend_android_exam.base

import com.chawin.ascend_android_exam.util.Network
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class BaseService<B, M> constructor(private val network: Network) {

    abstract suspend fun callApi(): B

    abstract fun mapper(from: B): M

    fun execute(): Flow<M> =
        flow { emit(checkInternetConnection()) }
            .map { callApi() }
            .map { validateResponse(it) }
            .map { mapper(it) }
            .catch { e: Throwable -> mapError(e) }

    private fun validateResponse(result: B): B {
//        Timber.d()
        return onResponse(result)
    }

    private fun checkInternetConnection() {
        if (!network.isConnected())
            throw ServiceException(
                NO_INTERNET_CONNECTION,
                NO_INTERNET_CONNECTION
            )
    }

    // Override and not implements when want response is Unit
    open fun onResponse(result: B): B {
        return result ?: throw ServiceException(RESPONSE_IS_NULL)
    }

    private fun mapError(e: Throwable) {
        when (e) {
            is TimeoutException ->
                throw ServiceException(TIMEOUT, e.message)
            is SocketTimeoutException ->
                throw ServiceException(SOCKET_TIMEOUT, e.message)
            is UnknownHostException ->
                throw ServiceException(UNKNOWN_HOST, e.message)
            is HttpException ->
                throw ServiceException(e.code().toString())
            is ServiceException ->
                throw ServiceException(e.errorCd, e.message)
            else ->
                throw ServiceException(APP_COMMON_ERROR, e.message)
        }
    }
}
