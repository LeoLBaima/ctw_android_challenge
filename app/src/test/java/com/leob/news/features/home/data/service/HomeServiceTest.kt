package com.leob.news.features.home.data.service

import com.leob.news.features.home.data.models.NewsResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HomeServiceTest {

    private lateinit var server: MockWebServer
    private lateinit var service: HomeService

    @Before
    fun setUp() {
        server = MockWebServer()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(HomeService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getNews sends expected path and query params and parses response`() = runTest {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    """
                    {
                      "articles": [
                        {
                          "author": "a",
                          "title": "t",
                          "description": "d",
                          "url": "u",
                          "urlToImage": "img",
                          "publishedAt": "p",
                          "content": "c"
                        }
                      ]
                    }
                    """.trimIndent()
                )
        )

        val sources = "cnn"
        val apiKey = "key"

        val parsed: NewsResponse = service.getNews(sources = sources, apiKey = apiKey)

        val request = server.takeRequest()
        assertEquals("/v2/top-headlines", request.requestUrl?.encodedPath)
        assertEquals(sources, request.requestUrl?.queryParameter("sources"))
        assertEquals(apiKey, request.requestUrl?.queryParameter("apiKey"))

        assertNotNull(parsed.articles)
        assertEquals(1, parsed.articles?.size)
        assertEquals("img", parsed.articles?.first()?.urlToImage)
    }
}
