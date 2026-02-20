package com.leob.news.features.home.data.models

import com.leob.news.features.home.domain.models.Source

data class SourceResponse(
    val id: String?,
    val name: String?
)

fun SourceResponse.toDomain(): Source = Source(
    id = id,
    name = name
)
