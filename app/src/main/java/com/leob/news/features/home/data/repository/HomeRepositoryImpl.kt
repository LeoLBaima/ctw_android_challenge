package com.leob.news.features.home.data.repository

import com.leob.news.BuildConfig
import com.leob.news.features.home.data.models.toDomain
import com.leob.news.features.home.data.service.HomeService
import com.leob.news.features.home.domain.models.News
import com.leob.news.features.home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService
) : HomeRepository {

    override suspend fun getNews(): News {
        val sources = BuildConfig.NEWS_SOURCE.trim()

        return homeService.getNews(
            sources = sources,
            apiKey = BuildConfig.NEWS_API_KEY.takeIf { it.isNotBlank() }
        ).toDomain()
    }
}