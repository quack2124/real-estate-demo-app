package com.app.realestatedemoapp.data.mapper

import com.app.realestatedemoapp.data.local.entity.PropertyEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class PropertyEntityToDomainMapperTest {

    @Test
    fun `PropertyEntity toDomain maps entity to model with correct property values`() {
        val entity = PropertyEntity(
            id = 1L,
            title = "Beautiful Apartment",
            price = 150000L,
            locality = "Zurich",
            street = "Example street 1",
            imageUrl = "https://example.com/image.jpg",
            isBookmarked = true,
            currency = "CHF"
        )

        val domainModel = entity.toDomain()

        assertEquals(1L, domainModel.id)
        assertEquals("Beautiful Apartment", domainModel.title)
        assertEquals(150000L, domainModel.price)
        assertEquals("Zurich", domainModel.locality)
        assertEquals("Example street 1", domainModel.street)
        assertEquals("https://example.com/image.jpg", domainModel.imageUrl)
        assertEquals(true, domainModel.isBookmarked)
        assertEquals("CHF", domainModel.currency)
    }
}
