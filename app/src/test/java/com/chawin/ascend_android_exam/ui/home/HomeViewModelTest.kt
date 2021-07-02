package com.chawin.ascend_android_exam.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.chawin.ascend_android_exam.base.ServiceException
import com.chawin.ascend_android_exam.domain.home.GetProductsUseCase
import com.chawin.ascend_android_exam.domain.home.HomeRepository
import com.chawin.ascend_android_exam.domain.home.Product
import com.chawin.ascend_android_exam.util.test.MainCoroutineRule
import com.chawin.ascend_android_exam.util.test.getOrAwaitValue
import com.chawin.ascend_android_exam.util.test.runBlocking
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.*

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repositoryHome: HomeRepository = mock()
    private val observeProducts: Observer<List<HomeInfo>> = mock()
    private val observeLoading: Observer<Boolean> = mock()
    private val observeShowEmptyLayout: Observer<Boolean> = mock()
    private val observeDialogError: Observer<String> = mock()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        val getProductsUseCase = GetProductsUseCase(repositoryHome)
        viewModel = HomeViewModel(getProductsUseCase)
        viewModel.products.observeForever(observeProducts)
        viewModel.loading.observeForever(observeLoading)
        viewModel.showEmptyLayout.observeForever(observeShowEmptyLayout)
        viewModel.dialogError.observeForever(observeDialogError)
    }

    @Test
    fun `get products success`() =
        mainCoroutineRule.runBlocking {
            given { repositoryHome.getProducts() }
                .willReturn {
                    flow {
                        emit(
                            listOf(
                                Product(
                                    "1",
                                    "Title",
                                    "https://image.com",
                                    "Content",
                                    true,
                                    "999.999"
                                )
                            )
                        )
                    }
                }
            viewModel.getProducts()

            // when
            viewModel.products.getOrAwaitValue()
            // then

            verify(observeLoading, times(1)).onChanged(true)
            verify(observeShowEmptyLayout, times(1)).onChanged(false)
            verify(observeDialogError, times(0)).onChanged(any())
            verify(observeLoading, times(1)).onChanged(false)
            Assert.assertTrue(viewModel.products.value?.get(0)?.isNewProduct == true)
            Assert.assertEquals(
                viewModel.products.value?.get(0)?.id,
                "1"
            )
            Assert.assertNotEquals(viewModel.products.value, null)
        }


    @Test
    fun `should show no data view`() {
        val productList: List<Product> = emptyList()
        mainCoroutineRule.runBlocking {
            given { repositoryHome.getProducts() }
                .willReturn {
                    flow {
                        emit(productList)
                    }
                }

            viewModel.getProducts()

            val loadingArgumentCaptor = argumentCaptor<Boolean>()
            verify(observeLoading, times(2)).onChanged(loadingArgumentCaptor.capture())
            Assert.assertEquals(loadingArgumentCaptor.firstValue, true)
            Assert.assertEquals(loadingArgumentCaptor.secondValue, false)
            verify(observeShowEmptyLayout, times(1)).onChanged(true)
            verify(observeDialogError, times(0)).onChanged(any())
            Assert.assertEquals(viewModel.products.value, null)
        }
    }

    @Test
    fun `should show error popup dialog`() {
        val expectedErrorCode = "0001"

        mainCoroutineRule.runBlocking {
            given { repositoryHome.getProducts() } .willReturn {
                flow {
                    throw ServiceException(
                        expectedErrorCode,
                        ""
                    )
                }
            }

            viewModel.getProducts()

            Assert.assertEquals(viewModel.products.value, null)
        }
    }


    @After
    fun tearDown() {
        viewModel.products.removeObserver(observeProducts)
        viewModel.loading.removeObserver(observeLoading)
        viewModel.showEmptyLayout.removeObserver(observeShowEmptyLayout)
        viewModel.dialogError.removeObserver(observeDialogError)

        reset(
            observeProducts,
            observeLoading,
            observeShowEmptyLayout,
            observeDialogError,
            repositoryHome
        )

        mainCoroutineRule.testDispatcher.cleanupTestCoroutines()
    }
}