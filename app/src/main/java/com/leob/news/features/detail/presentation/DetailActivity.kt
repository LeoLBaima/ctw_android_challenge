package com.leob.news.features.detail.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leob.news.R
import com.leob.news.commons.components.DefaultTopBar
import com.leob.news.commons.components.HeadlineImage
import com.leob.news.commons.extensions.openExternalUrl
import com.leob.news.commons.extensions.toDisplayPublishedAt
import com.leob.news.features.home.domain.models.Article
import com.leob.news.ui.theme.NewsTheme

class DetailActivity : ComponentActivity() {
    val article by lazy {
        //I'm using this deprecated cause the minimum SDK is 33 and the configured locally is 24
        intent.getParcelableExtra("article") as Article?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current

            NewsTheme {
                Scaffold(
                    topBar = {
                        DefaultTopBar(
                            title = stringResource(R.string.app_name),
                            onBackClick = { finish() },
                        )
                    }) { innerPadding ->
                    val publishedAt = article?.publishedAt.toDisplayPublishedAt()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            HeadlineImage(
                                imageUrl = article?.imageUrl,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Text(
                                text = article?.title.orEmpty(),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.SemiBold, lineHeight = 28.sp
                                ),
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = publishedAt,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = article?.description.orEmpty(),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 22.sp
                                ),
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = article?.content.orEmpty(),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 13.sp, lineHeight = 20.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                val opened = context.openExternalUrl(article?.url)
                                if (!opened) {
                                    Toast.makeText(
                                            context, "Can't open this link", Toast.LENGTH_SHORT
                                        ).show()
                                }
                            },
                            enabled = article != null,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.app_color))
                        ) {
                            Text(text = "Read more")
                        }
                    }
                }
            }
        }
    }
}