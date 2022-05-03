package jp.co.yumemi.android.codeCheck.ui.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.codeCheck.data.model.GitRepo
import jp.co.yumemi.android.codeCheck.ui.components.SearchHeader

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SearchScreen(
    repositories: List<GitRepo>,
    navigateToDetail: (GitRepo) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        SearchHeader()
        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            items(repositories) {
                GitRepoRow(
                    name = it.name,
                    onClick = { navigateToDetail(it) }
                )
            }
        }
    }
}

@Composable
fun GitRepoRow(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(1f)
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp
            ),
        elevation = 4.dp
    ) {
        GitRepoRowContent(modifier = modifier, name)
    }
}

@Composable
fun GitRepoRowContent(modifier: Modifier, name: String) {
    Column(
        modifier = modifier.padding(12.dp)
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
