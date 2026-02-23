package com.leob.news.features.home.data.models

import org.junit.Assert.assertEquals
import org.junit.Test

class NewsResponseTest {

    @Test
    fun `GIVEN NewsResponse with articles WHEN toDomain THEN maps articles list`() {
        val response = NewsResponse(
            articles = listOf(
                ArticleResponse(
                    author = "a1",
                    title = "t1",
                    description = null,
                    url = "u1",
                    urlToImage = "i1",
                    publishedAt = null,
                    content = null
                ),
                ArticleResponse(
                    author = "a2",
                    title = "t2",
                    description = "d2",
                    url = "u2",
                    urlToImage = null,
                    publishedAt = "p2",
                    content = "c2"
                )
            )
        )

        val domain = response.toDomain()

        assertEquals(2, domain.articles.size)
        assertEquals("a1", domain.articles[0].author)
        assertEquals("i1", domain.articles[0].imageUrl)
        assertEquals("a2", domain.articles[1].author)
        assertEquals(null, domain.articles[1].imageUrl)
    }

    @Test
    fun `GIVEN NewsResponse with null articles WHEN toDomain THEN returns empty list`() {
        val response = NewsResponse(articles = null)

        val domain = response.toDomain()

        assertEquals(emptyList<Any>(), domain.articles)
    }
}
