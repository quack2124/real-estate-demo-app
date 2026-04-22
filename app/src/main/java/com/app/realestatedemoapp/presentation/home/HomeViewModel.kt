package com.app.realestatedemoapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.realestatedemoapp.R
import com.app.realestatedemoapp.domain.NetworkConnectivityObserver
import com.app.realestatedemoapp.domain.PropertyRepository
import com.app.realestatedemoapp.domain.model.NetworkStatus
import com.app.realestatedemoapp.domain.usecases.UpdateBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val propertyRepository: PropertyRepository,
    val updateBookmarkUseCase: UpdateBookmarkUseCase,
    val connectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    val networkStatus = connectivityObserver.observe().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NetworkStatus.Connected
    )

    private val _errorMessage = MutableStateFlow<Int?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        refreshProperties()
    }

    fun refreshProperties() {
        viewModelScope.launch(Dispatchers.IO) {
            if (connectivityObserver.observe().first() == NetworkStatus.Connected) {
                propertyRepository.refreshProperties().onFailure {
                    _errorMessage.value = R.string.failed_to_fetch_properties
                }
            }
        }
    }

    val properties = propertyRepository.getProperties()
        .cachedIn(viewModelScope).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    fun updateBookmark(propertyId: Long, isBookmarked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmarkUseCase(
                propertyId,
                isBookmarked
            ).onFailure {
                _errorMessage.value = R.string.failed_to_update_bookmark_state
            }
        }
    }

    fun dismissError() {
        _errorMessage.value = null
    }
}