package com.app.realestatedemoapp.domain.usecases

import com.app.realestatedemoapp.domain.PropertyRepository
import javax.inject.Inject

class UpdateBookmarkUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {

    suspend operator fun invoke(propertyId: Long, isBookmarked: Boolean): Result<Unit> {
        return propertyRepository.updatePropertyBookmark(propertyId, isBookmarked)
    }
}