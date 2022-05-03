package jp.co.yumemi.android.codeCheck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.data.model.GitRepo

@Composable
fun DetailCard(gitRepo: GitRepo) {
    Card(
        modifier = Modifier.clickable(onClick = {}),
        elevation = 8.dp
    ) {
        DetailCardContent(gitRepo)
    }
}

@Composable
fun DetailCardContent(gitRepo: GitRepo) {
    Column {
        Surface(
            color = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Favicon(url = gitRepo.ownerIconUrl.avatarUrl, Modifier.padding(20.dp))
        }
        Column(
            Modifier.padding(16.dp)
        ) {
            Text(
                text = gitRepo.name,
                fontSize = 20.sp
            )
            Spacer(Modifier.padding(8.dp))
            GitRepoStatusView(gitRepo)
        }
    }
}

@Composable
fun GitRepoStatusView(gitRepo: GitRepo) {

    val langView = gitRepo.language.let {
        if (it.isNullOrEmpty()) {
            stringResource(R.string.no_language)
        } else {
            stringResource(R.string.written_language, it)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Column {
                Text(
                    text = stringResource(R.string.stars_view, gitRepo.stargazersCount),
                    fontSize = gitStatusSize
                )
                Text(
                    text = stringResource(R.string.watchers_view, gitRepo.watchersCount),
                    fontSize = gitStatusSize
                )
                Text(
                    text = stringResource(R.string.forks_view, gitRepo.forksCount),
                    fontSize = gitStatusSize
                )
                Text(
                    text = stringResource(R.string.openIssues_view, gitRepo.openIssuesCount),
                    fontSize = gitStatusSize
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = langView)
                Icon(imageVector = Icons.Filled.Link, contentDescription = null)
            }
        }
    }
}

val gitStatusSize = 12.sp

@Composable
fun Favicon(
    url: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                error(R.drawable.ic_launcher_foreground)
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }
        ),
        contentDescription = null,
        modifier = modifier
    )
}
