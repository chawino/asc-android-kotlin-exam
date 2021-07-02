package com.chawin.ascend_android_exam.ui.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.chawin.ascend_android_exam.domain.home.GetProductsUseCase
import com.chawin.ascend_android_exam.domain.home.Product
import com.chawin.ascend_android_exam.util.SingleLiveEvent
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _dialogError = LiveEvent<String>()
    val dialogError: LiveData<String> by lazy { _dialogError }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _loading = LiveEvent<Boolean>()
    val loading: LiveData<Boolean> by lazy { _loading }

    private var _showEmptyLayout = LiveEvent<Boolean>()
    val showEmptyLayout: LiveData<Boolean> by lazy { _showEmptyLayout }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<HomeInfo>> =
        Transformations.map(_products, this::transformProducts)

    private val _navigateToProductDetail = LiveEvent<HomeInfo>()
    val navigateToProductDetail: LiveData<HomeInfo> = _navigateToProductDetail

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase.execute(Unit)
                .flowOn(Dispatchers.IO)
                .onStart {
                    _loading.value = true
                }
                .onCompletion { _loading.value = false }
                .catch { e ->
                    _loading.value = false
                    _showEmptyLayout.value = true
                    showErrorOnLoad(e)
                }
                .collect {
                    if (it.isNotEmpty()) {
                        _showEmptyLayout.value = false
                        _products.value = it
                    } else {
                        _showEmptyLayout.value = true
                    }
                }
        }
    }

    private fun showErrorOnLoad(e: Throwable) {
        _dialogError.value = e.message
    }

    fun openDessertDetail(product: HomeInfo) {
        _navigateToProductDetail.value = product
    }

    private fun transformProducts(products: List<Product>): List<HomeInfo> {
        return products.map {
            HomeInfo(
                id = it.id,
                title = it.title,
                image = it.image,
                content = it.content,
                isNewProduct = it.isNewProduct,
                price = it.price
            )
        }
    }
}


