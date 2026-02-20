package com.leob.news.features.home.domain.repository

import com.leob.news.features.home.domain.models.News

interface HomeRepository {
    suspend fun getNews(): News
}