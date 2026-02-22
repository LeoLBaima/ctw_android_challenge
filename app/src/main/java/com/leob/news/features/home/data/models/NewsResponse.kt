package com.leob.news.features.home.data.models

import com.leob.news.features.home.domain.models.News
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val articles: List<ArticleResponse>?
)

fun NewsResponse.toDomain(): News = News(
    articles = articles.orEmpty().map { it.toDomain() }
)
