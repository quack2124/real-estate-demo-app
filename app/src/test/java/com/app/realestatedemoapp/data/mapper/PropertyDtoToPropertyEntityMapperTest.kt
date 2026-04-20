package com.app.realestatedemoapp.data.mapper

import com.app.realestatedemoapp.data.remote.dto.AddressDto
import com.app.realestatedemoapp.data.remote.dto.AttachmentDto
import com.app.realestatedemoapp.data.remote.dto.LocaleDto
import com.app.realestatedemoapp.data.remote.dto.LocalizationDto
import com.app.realestatedemoapp.data.remote.dto.PriceDetailsDto
import com.app.realestatedemoapp.data.remote.dto.PriceDto
import com.app.realestatedemoapp.data.remote.dto.PropertyDto
import com.app.realestatedemoapp.data.remote.dto.TitleDto
import org.junit.Assert.assertEquals
import org.junit.Test

class PropertyDtoToPropertyEntityMapperTest {

    @Test
    fun `PropertyDto toEntity maps dto to entity with correct property values`() {
        val propertyDto = PropertyDto(
            id = 1L, localization = LocalizationDto(
                locale = LocaleDto(
                    title = TitleDto(text = "Beautiful Apartment"),
                    attachments = listOf(AttachmentDto(url = "https://example.com/image.jpg"))
                )
            ), priceDetails = PriceDetailsDto(
                priceDto = PriceDto(amount = 150000L), currency = "CHF"
            ), address = AddressDto(
                locality = "Zurich", street = "Example street 123"
            )
        )
        val entity = propertyDto.toEntity()

        assertEquals(1L, entity.id)
        assertEquals("Beautiful Apartment", entity.title)
        assertEquals(150000L, entity.price)
        assertEquals("Zurich", entity.locality)
        assertEquals(
            "Example street 123",
            entity.street
        ) // Asserting the fallback to an empty string worked
        assertEquals("https://example.com/image.jpg", entity.imageUrl)
        assertEquals(false, entity.isBookmarked) // defaults to false
        assertEquals("CHF", entity.currency)
    }
}

