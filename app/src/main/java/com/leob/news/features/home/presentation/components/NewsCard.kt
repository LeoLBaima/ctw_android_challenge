package com.leob.news.features.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NewsCard(title: String) {
    Card {
        Column {
            Text(text = title)
        }
    }
}