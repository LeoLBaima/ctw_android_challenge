package com.leob.news.features.home.data.models

import com.leob.news.features.home.domain.models.News

data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleResponse>?
)

fun NewsResponse.toDomain(): News = News(
    status = status.orEmpty(),
    totalResults = totalResults ?: 0,
    articles = articles.orEmpty().map { it.toDomain() }
)
