package com.app.realestatedemoapp.presentation.bookmark

import androidx.paging.PagingData
import androidx.paging.map
import com.app.realestatedemoapp.R
import com.app.realestatedemoapp.domain.PropertyRepository
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
class BookmarkViewModelTest {

    private lateinit var viewModel: BookmarkViewModel
    private val updateBookmarkUseCase = mockk<UpdateBookmarkUseCase>()
    private val propertyRepository = mockk<PropertyRepository>()

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
    fun `updateBookmark sets error message on error`() = runTest(testDispatcher) {
        coEvery { updateBookmarkUseCase(21313, false) } returns Result.failure(
            RuntimeException("Update Failed")
        )
        every { propertyRepository.getBookmarkedProperties() } returns emptyFlow()

        viewModel = BookmarkViewModel(propertyRepository, updateBookmarkUseCase)

        viewModel.updateBookmark(21313, false)

        advanceUntilIdle()

        Assert.assertEquals(
            R.string.failed_to_update_bookmark_state,
            viewModel.errorMessageId.value
        )

    }

    @Test
    fun `updateBookmark sets isBookmarked property on success and populates it in bookmarkedProperties property`() =
        runTest(testDispatcher) {
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
            every { propertyRepository.getBookmarkedProperties() } returns flowPagingData
            viewModel = BookmarkViewModel(propertyRepository, updateBookmarkUseCase)
            viewModel.updateBookmark(21313, true)
            advanceUntilIdle()

            val result = viewModel.bookmarkedProperties.first()

            result.map {
                Assert.assertEquals(true, it.isBookmarked)
            }

        }
}