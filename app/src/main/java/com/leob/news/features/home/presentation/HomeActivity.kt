package com.leob.news.features.home.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leob.news.R
import com.leob.news.commons.components.DefaultTopBar
import com.leob.news.commons.extensions.toDisplayPublishedAt
import com.leob.news.features.home.domain.models.News
import com.leob.news.features.home.presentation.components.NewsCard
import com.leob.news.features.home.presentation.viewmodel.HomeViewModel
import com.leob.news.features.home.presentation.viewmodel.UiState
import com.leob.news.ui.theme.Background
import com.leob.news.ui.theme.Grey
import com.leob.news.ui.theme.NewsTheme
import com.leob.news.ui.theme.Purple40
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Background,
                    topBar = {
                        DefaultTopBar(
                            title = stringResource(R.string.app_name),
                        )
                    }
                ) { innerPadding ->
                    Column {
                        val uiState by viewModel.uiState.observeAsState()
                        when (uiState) {
                            is UiState.Loading -> LoadingContent(innerPadding)
                            is UiState.Success -> SuccessContent(
                                innerPadding,
                                (uiState as UiState.Success).news
                            )

                            is UiState.Error -> Text(text = "Dan dan dannnn")
                            else -> {}
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun LoadingContent(innerPadding: PaddingValues) {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            CircularProgressIndicator(
                color = Purple40,
            )
        }

    }

    @Composable
    fun SuccessContent(innerPadding: PaddingValues, news: News) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            news.articles.forEachIndexed { index, article ->
                val publishedAt = article.publishedAt.toDisplayPublishedAt()
                NewsCard(
                    imageUrl = article.imageUrl,
                    title = article.title ?: "",
                    description = publishedAt.takeIf { it.isNotBlank() }
                        ?.let { "Published at: $it" }
                        .orEmpty(),
                )
                if (index < news.articles.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = DividerDefaults.Thickness,
                        color = Grey,
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}