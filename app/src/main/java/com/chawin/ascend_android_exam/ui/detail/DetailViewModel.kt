package com.chawin.ascend_android_exam.ui.detail

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.chawin.ascend_android_exam.domain.detail.GetProductUseCase
import com.chawin.ascend_android_exam.domain.home.Product
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {
    private val productId = MutableLiveData<String>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _dialogError = LiveEvent<String>()
    val dialogError: LiveData<String> by lazy { _dialogError }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _loading = LiveEvent<Boolean>()
    val loading: LiveData<Boolean> by lazy { _loading }

    private var _showEmptyLayout = LiveEvent<Boolean>()
    val showEmptyLayout: LiveData<Boolean> by lazy { _showEmptyLayout }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _product = MutableLiveData<Product>()
    val product: LiveData<DetailInfo> =
        Transformations.map(_product, this::transformDetail)

    private val _navigateToProductDetail = LiveEvent<DetailInfo>()
    val navigateToProductDetail: LiveData<DetailInfo> = _navigateToProductDetail

    fun initialize(productId: String) {
        this.productId.value = productId
        getProduct(productId)
    }

    private fun getProduct(productId: String) {
        viewModelScope.launch {
            getProductUseCase.execute(productId)
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
                    _showEmptyLayout.value = false
                    _product.value = it
                }
        }
    }


    private fun showErrorOnLoad(e: Throwable) {
        _dialogError.value = e.message
    }

    private fun transformDetail(product: Product): DetailInfo = DetailInfo(
        id = product.id,
        title = product.title,
        image = product.image,
        content = product.content,
        isNewProduct = product.isNewProduct,
        price = product.price
    )
}


