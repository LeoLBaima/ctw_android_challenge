package com.leob.news.features.home.data.repository

import com.leob.news.BuildConfig
import com.leob.news.features.home.data.models.ArticleResponse
import com.leob.news.features.home.data.models.NewsResponse
import com.leob.news.features.home.data.service.HomeService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeRepositoryImplTest {

    @Test
    fun `GIVEN HomeService response WHEN getNews THEN trims sources forwards optional apiKey and maps to domain`() = runTest {
        val service = mockk<HomeService>()

        val dto = NewsResponse(
            articles = listOf(
                ArticleResponse(
                    author = "a",
                    title = "t",
                    description = null,
                    url = "u",
                    urlToImage = "img",
                    publishedAt = "p",
                    content = "c"
                )
            )
        )

        val expectedSources = BuildConfig.NEWS_SOURCE.trim()
        val expectedApiKey = BuildConfig.NEWS_API_KEY.takeIf { it.isNotBlank() }

        coEvery { service.getNews(sources = expectedSources, apiKey = expectedApiKey) } returns dto

        val repo = HomeRepositoryImpl(service)

        val result = repo.getNews()

        coVerify(exactly = 1) { service.getNews(sources = expectedSources, apiKey = expectedApiKey) }
        assertEquals(1, result.articles.size)
        assertEquals("img", result.articles.first().imageUrl)
    }
}
