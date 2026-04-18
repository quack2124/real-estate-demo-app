package com.app.realestatedemoapp.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.realestatedemoapp.domain.PropertyRepository
import com.app.realestatedemoapp.domain.usecases.UpdateBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    propertyRepository: PropertyRepository,
    val updateBookmarkUseCase: UpdateBookmarkUseCase
) :
    ViewModel() {

    val bookmarkedProperties =
        propertyRepository.getBookmarkedProperties().cachedIn(viewModelScope).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    fun updateBookmark(propertyId: Long, isBookmarked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmarkUseCase(
                propertyId,
                isBookmarked
            )
        }
    }
}