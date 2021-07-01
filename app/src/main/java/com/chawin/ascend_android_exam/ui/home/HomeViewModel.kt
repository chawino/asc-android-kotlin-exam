package com.chawin.ascend_android_exam.ui.home

import androidx.lifecycle.*
import com.chawin.ascend_android_exam.domain.home.GetProductsUseCase
import com.chawin.ascend_android_exam.domain.home.Product
import com.chawin.ascend_android_exam.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _dialogError = SingleLiveEvent<String>()
    val dialogError: SingleLiveEvent<String> = _dialogError

    private val _loading = SingleLiveEvent<Boolean>()
    val loading: SingleLiveEvent<Boolean> = _loading

    private var _showEmptyLayout = SingleLiveEvent<Boolean>()
    val showEmptyLayout: SingleLiveEvent<Boolean> = _showEmptyLayout

    private val _prpducts = MutableLiveData<List<Product>>()
    val product: LiveData<List<HomeInfo>> =
        Transformations.map(_prpducts, this::transformProducts)

    private val _navigateToProductDetail = SingleLiveEvent<HomeInfo>()
    val navigateToProductDetail: SingleLiveEvent<HomeInfo> =_navigateToProductDetail

    init {
        getProducts()
    }

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
                        _prpducts.value = it
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


