package com.app.realestatedemoapp.presentation.home

import androidx.paging.PagingData
import androidx.paging.map
import com.app.realestatedemoapp.R
import com.app.realestatedemoapp.domain.NetworkConnectivityObserver
import com.app.realestatedemoapp.domain.PropertyRepository
import com.app.realestatedemoapp.domain.model.NetworkStatus
import com.app.realestatedemoapp.domain.model.PropertyModel
import com.app.realestatedemoapp.domain.usecases.UpdateBookmarkUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val propertyRepository = mockk<PropertyRepository>()
    private val updateBookmarkUseCase = mockk<UpdateBookmarkUseCase>()
    private val connectivityObserver = mockk<NetworkConnectivityObserver>()

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `refreshProperties sets error message on repository failure`() = runTest(testDispatcher) {
        val exception = RuntimeException("Network Error")
        coEvery { propertyRepository.refreshProperties() } returns Result.failure(exception)
        every { connectivityObserver.observe() } returns flowOf(NetworkStatus.Connected)
        every { propertyRepository.getProperties() } returns Result.success(emptyFlow())

        viewModel = HomeViewModel(propertyRepository, updateBookmarkUseCase, connectivityObserver)
        viewModel.refreshProperties()

        advanceUntilIdle()

        Assert.assertEquals(R.string.failed_to_fetch_properties, viewModel.errorMessage.value)
    }


    @Test
    fun `refreshProperties sets error message on connection failure`() = runTest(testDispatcher) {
        coEvery { propertyRepository.refreshProperties() } returns Result.success(Unit)
        every { connectivityObserver.observe() } returns flowOf(NetworkStatus.Disconnected)
        every { propertyRepository.getProperties() } returns Result.success(emptyFlow())

        viewModel = HomeViewModel(propertyRepository, updateBookmarkUseCase, connectivityObserver)
        viewModel.refreshProperties()
        advanceUntilIdle()

        Assert.assertEquals(R.string.no_internet_connection, viewModel.errorMessage.value)
    }

    @Test
    fun `refreshProperties completes and updates properties flow`() = runTest(testDispatcher) {
        val flowPagingData = flowOf(
            PagingData.from(
                listOf(
                    PropertyModel(
                        id = 1,
                        title = "Test",
                        price = 333333333,
                        locality = "Test",
                        street = "Test",
                        imageUrl = "https://example.com/image.jpg",
                        isBookmarked = false,
                        currency = "CHF"
                    )
                )
            )
        )

        coEvery { propertyRepository.refreshProperties() } returns Result.success(Unit)
        every { connectivityObserver.observe() } returns flowOf(NetworkStatus.Connected)
        every { propertyRepository.getProperties() } returns Result.success(flowPagingData)

        viewModel = HomeViewModel(propertyRepository, updateBookmarkUseCase, connectivityObserver)
        viewModel.refreshProperties()
        advanceUntilIdle()

        Assert.assertNotNull(viewModel.properties)
        Assert.assertEquals(flowPagingData, viewModel.properties)
    }

    @Test
    fun `updateBookmark sets error message on error`() = runTest(testDispatcher) {
        coEvery { updateBookmarkUseCase(21313, false) } returns Result.failure(
            RuntimeException("Update Failed")
        )
        every { propertyRepository.getBookmarkedProperties() } returns Result.success(emptyFlow())
        every { connectivityObserver.observe() } returns flowOf(NetworkStatus.Disconnected)
        every { propertyRepository.getProperties() } returns Result.success(emptyFlow())

        viewModel = HomeViewModel(propertyRepository, updateBookmarkUseCase, connectivityObserver)
        viewModel.updateBookmark(21313, false)

        advanceUntilIdle()

        Assert.assertEquals(R.string.failed_to_update_bookmark_state, viewModel.errorMessage.value)

    }

    @Test
    fun `updateBookmark sets isBookmarked property on success`() = runTest(testDispatcher) {
        val flowPagingData = flowOf(
            PagingData.from(
                listOf(
                    PropertyModel(
                        id = 1,
                        title = "Test",
                        price = 333333333,
                        locality = "Test",
                        street = "Test",
                        imageUrl = "https://example.com/image.jpg",
                        isBookmarked = false,
                        currency = "CHF"
                    )
                )
            )
        )

        coEvery { updateBookmarkUseCase(21313, true) } returns Result.success(Unit)
        every { connectivityObserver.observe() } returns flowOf(NetworkStatus.Disconnected)
        every { propertyRepository.getProperties() } returns Result.success(flowPagingData)
        viewModel = HomeViewModel(propertyRepository, updateBookmarkUseCase, connectivityObserver)
        viewModel.updateBookmark(21313, true)
        advanceUntilIdle()

        val result = viewModel.properties?.first()

        result?.map {
            Assert.assertEquals(true, it.isBookmarked)
        }
    }

}