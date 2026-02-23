package com.leob.news.features.home.data.models

import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleResponseTest {

    @Test
    fun `GIVEN ArticleResponse WHEN toDomain THEN maps fields AND renames urlToImage to imageUrl`() {
        val dto = ArticleResponse(
            author = "a",
            title = "t",
            description = "d",
            url = "https://example.com",
            urlToImage = "https://example.com/image.png",
            publishedAt = "2026-01-01T00:00:00Z",
            content = "c"
        )

        val domain = dto.toDomain()

        assertEquals("a", domain.author)
        assertEquals("t", domain.title)
        assertEquals("d", domain.description)
        assertEquals("https://example.com", domain.url)
        assertEquals("https://example.com/image.png", domain.imageUrl)
        assertEquals("2026-01-01T00:00:00Z", domain.publishedAt)
        assertEquals("c", domain.content)
    }
}
