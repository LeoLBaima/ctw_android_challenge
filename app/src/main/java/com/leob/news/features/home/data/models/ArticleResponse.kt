package com.leob.news.features.home.data.models

import com.leob.news.features.home.domain.models.Article

data class ArticleResponse(
    val source: SourceResponse?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

fun ArticleResponse.toDomain(): Article = Article(
    source = source?.toDomain(),
    author = author,
    title = title,
    description = description,
    url = url,
    imageUrl = urlToImage,
    publishedAt = publishedAt,
    content = content
)
