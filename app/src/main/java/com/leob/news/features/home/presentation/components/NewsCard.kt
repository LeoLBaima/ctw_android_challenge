package com.leob.news.features.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewsCard(title: String, description: String, imageUrl: String?) {
    Column {
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            HeadlineImage(imageUrl = imageUrl)
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            Text(text = title)
        }
        Text(text = description, fontSize = 12.sp)
    }
}