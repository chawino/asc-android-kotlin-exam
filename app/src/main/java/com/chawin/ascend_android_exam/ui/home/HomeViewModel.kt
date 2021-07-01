package com.chawin.ascend_android_exam.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chawin.ascend_android_exam.domain.home.GetProductsUseCase
import com.chawin.ascend_android_exam.domain.home.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _dialogError = MutableLiveData<String>()
    val dialogError: LiveData<String> = _dialogError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _showEmptyLayout = MutableLiveData<Boolean>()
    val showEmptyLayout: LiveData<Boolean> = _showEmptyLayout

    private val _home = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>> = _home

    private val _navigateToProductDetail = MutableLiveData<Product>()
    val navigateToProductDetail: LiveData<Product>
        get() = _navigateToProductDetail

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase.execute(Unit, false)
                .flowOn(Dispatchers.IO)
                .onStart {
                    _loading.value = true
                    _showEmptyLayout.value = false
                }
                .onCompletion { _loading.value = false }
                .catch { e ->
                    _showEmptyLayout.value = true
                    showErrorOnLoad(e)
                }
                .collect {
                    if (it.isNotEmpty()) {
                        _showEmptyLayout.value = false
                        _home.value = it
                    } else {
                        _showEmptyLayout.value = true
                    }
                }
        }
    }

    private fun showErrorOnLoad(e: Throwable) {
        _dialogError.value = e.message
    }

    fun openDessertDetail(product: Product) {
        _navigateToProductDetail.value = product
    }


}