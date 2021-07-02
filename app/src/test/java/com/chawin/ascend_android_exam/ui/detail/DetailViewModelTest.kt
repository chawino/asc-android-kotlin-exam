package com.chawin.ascend_android_exam.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.chawin.ascend_android_exam.domain.detail.DetailRepository
import com.chawin.ascend_android_exam.domain.detail.GetProductUseCase
import com.chawin.ascend_android_exam.domain.home.Product
import com.chawin.ascend_android_exam.ext.toAmountFormat2Digit
import com.chawin.ascend_android_exam.util.test.MainCoroutineRule
import com.chawin.ascend_android_exam.util.test.getOrAwaitValue
import com.chawin.ascend_android_exam.util.test.runBlocking
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.*

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repositoryDetail: DetailRepository = mock()
    private val observeProduct: Observer<DetailInfo> = mock()
    private val observeLoading: Observer<Boolean> = mock()
    private val observeShowEmptyLayout: Observer<Boolean> = mock()
    private val observeDialogError: Observer<String> = mock()
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        val getProductUseCase = GetProductUseCase(repositoryDetail)
        viewModel = DetailViewModel(getProductUseCase)
        viewModel.product.observeForever(observeProduct)
        viewModel.loading.observeForever(observeLoading)
        viewModel.showEmptyLayout.observeForever(observeShowEmptyLayout)
        viewModel.dialogError.observeForever(observeDialogError)
    }

    @Test
    fun `get product detail success`() =
        mainCoroutineRule.runBlocking {
            given {
                repositoryDetail.getProduct("1")
            }
                .willReturn {
                    flow {
                        emit(
                            Product(
                                "1",
                                "Title",
                                "https://image.com",
                                "Content",
                                true,
                                "999.999"
                            )
                        )
                    }
                }
            viewModel.initialize("1")
            // when
            viewModel.product.getOrAwaitValue()
            // then

            val loadingArgumentCaptor = argumentCaptor<Boolean>()
            verify(observeLoading, times(2)).onChanged(loadingArgumentCaptor.capture())
            Assert.assertEquals(loadingArgumentCaptor.firstValue, true)
            Assert.assertEquals(loadingArgumentCaptor.secondValue, false)
            verify(observeShowEmptyLayout, times(0)).onChanged(null)
            verify(observeDialogError, times(0)).onChanged(any())
            verify(observeProduct, times(1)).onChanged(any())
            Assert.assertEquals(viewModel.product.value?.isNewProduct, true)
            Assert.assertEquals(
                viewModel.product.value?.id,
                "1"
            )
            Assert.assertNotEquals(viewModel.product.value, null)
        }

    @Test
    fun `check data should be success`() =
        mainCoroutineRule.runBlocking {
            given { repositoryDetail.getProduct("1") }
                .willReturn {
                    flow {
                        emit(
                            Product(
                                "1",
                                "Title",
                                "https://image.com",
                                "Content",
                                true,
                                "999.999"
                            )
                        )
                    }
                }
            viewModel.initialize("1")
            viewModel.product.getOrAwaitValue()

            Assert.assertEquals(viewModel.product.value?.title, "Title")
            Assert.assertEquals(viewModel.product.value?.content, "Content")
            Assert.assertTrue(viewModel.product.value?.isNewProduct!!)
            Assert.assertEquals(
                "1,000.00",
                viewModel.product.value?.price.toAmountFormat2Digit()
            )
        }

    @After
    fun tearDown() {
        viewModel.product.removeObserver(observeProduct)
        viewModel.loading.removeObserver(observeLoading)
        viewModel.showEmptyLayout.removeObserver(observeShowEmptyLayout)
        viewModel.dialogError.removeObserver(observeDialogError)

        reset(
            observeProduct,
            observeLoading,
            observeShowEmptyLayout,
            observeDialogError,
            repositoryDetail
        )

        mainCoroutineRule.testDispatcher.cleanupTestCoroutines()
    }
}