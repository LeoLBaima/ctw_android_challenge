package com.leob.news.features.home.data.service

import com.leob.news.features.home.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String? = null
    ): NewsResponse
}