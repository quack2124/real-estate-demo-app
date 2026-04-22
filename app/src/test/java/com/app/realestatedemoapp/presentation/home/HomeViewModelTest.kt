package com.app.realestatedemoapp.presentation.home

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.app.realestatedemoapp.R
import com.app.realestatedemoapp.domain.NetworkConnectivityObserver
import com.app.realestatedemoapp.domain.PropertyRepository
import com.app.realestatedemoapp.domain.model.NetworkStatus
import com.app.realestatedemoapp.domain.model.PropertyModel
import com.app.realestatedemoapp.domain.usecases.UpdateBookmarkUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.emptyFlow
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
    private lateinit var propertyModel: PropertyModel
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        propertyModel = PropertyModel(
            id = 21313,
            title = "Test",
            price = 333333333,
            locality = "Test",
            street = "Test",
            imageUrl = "https://example.com/image.jpg",
            isBookmarked = false,
            currency = "CHF"
        )
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
        every { propertyRepository.getProperties() } returns emptyFlow()

        viewModel = HomeViewModel(propertyRepository, updateBookmarkUseCase, connectivityObserver)
        viewModel.refreshProperties()

        advanceUntilIdle()
        Assert.assertEquals(R.string.failed_to_fetch_properties, viewModel.errorMessage.value)
    }

    @Test
    fun `refreshProperties should not be called on connection failure`() = runTest(testDispatcher) {
        coEvery { propertyRepository.refreshProperties() } returns Result.success(Unit)
        every { connectivityObserver.observe() } returns flowOf(NetworkStatus.Disconnected)
        every { propertyRepository.getProperties() } returns emptyFlow()

        viewModel = HomeViewModel(propertyRepository, updateBookmarkUseCase, connectivityObserver)
        viewModel.refreshProperties()
        advanceUntilIdle()
        coVerify(exactly = 0) { propertyRepository.refreshProperties() }
    }

    @Test
    fun `refreshProperties completes and updates properties flow`() = runTest(testDispatcher) {
        val flowPagingData = flowOf(
            PagingData.from(
                listOf(
                    propertyModel
                )
            )
        )

        coEvery { propertyRepository.refreshProperties() } returns Result.success(Unit)
        every { connectivityObserver.observe() } returns flowOf(NetworkStatus.Connected)
        every { propertyRepository.getProperties() } returns flowPagingData

        viewModel = HomeViewModel(propertyRepository, updateBookmarkUseCase, connectivityObserver)
        advanceUntilIdle()

        viewModel.properties.drop(1).test {
            viewModel.refreshProperties()
            val item = awaitItem()
            val updatedSnapshot = flowOf(item).asSnapshot().first()

            Assert.assertEquals(propertyModel, updatedSnapshot)
        }
    }

    @Test
    fun `updateBookmark sets error message on error`() = runTest(testDispatcher) {
        coEvery { updateBookmarkUseCase(21313, false) } returns Result.failure(
            RuntimeException("Update Failed")
        )
        every { propertyRepository.getBookmarkedProperties() } returns emptyFlow()
        every { connectivityObserver.observe() } returns flowOf(NetworkStatus.Disconnected)
        every { propertyRepository.getProperties() } returns emptyFlow()

        viewModel = HomeViewModel(propertyRepository, updateBookmarkUseCase, connectivityObserver)
        viewModel.updateBookmark(21313, false)

        advanceUntilIdle()

        Assert.assertEquals(R.string.failed_to_update_bookmark_state, viewModel.errorMessage.value)
    }

    @Test
    fun `updateBookmark sets isBookmarked property on success`() = runTest(testDispatcher) {
        val flowPagingData = MutableStateFlow(
            PagingData.from(
                listOf(
                    propertyModel
                )
            )
        )

        coEvery { updateBookmarkUseCase(21313, true) } answers {
            flowPagingData.value = PagingData.from(listOf(propertyModel.copy(isBookmarked = true)))
            Result.success(Unit)
        }
        // disable connection to avoid mocking refresh endpoint
        every { connectivityObserver.observe() } returns flowOf(NetworkStatus.Disconnected)
        every { propertyRepository.getProperties() } returns flowPagingData
        viewModel = HomeViewModel(propertyRepository, updateBookmarkUseCase, connectivityObserver)
        viewModel.updateBookmark(21313, true)
        advanceUntilIdle()

        // Skip initial state with drop
        viewModel.properties.drop(1).test {
            viewModel.updateBookmark(21313, true)
            val item = awaitItem()
            val updatedSnapshot = flowOf(item).asSnapshot()

            Assert.assertEquals(true, updatedSnapshot.first().isBookmarked)
        }
    }
}