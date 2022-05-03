package jp.co.yumemi.android.codeCheck.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.codeCheck.data.model.GitRepo
import jp.co.yumemi.android.codeCheck.data.model.Owner
import jp.co.yumemi.android.codeCheck.ui.components.DetailCard
import jp.co.yumemi.android.codeCheck.ui.theme.CodeCheckTheme

@Composable
fun DetailsScreen(gitRepo: GitRepo) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        DetailCard(gitRepo)
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    CodeCheckTheme {
        DetailsScreen(gitRepo = gitRepo)
    }
}

val gitRepo = GitRepo(
    name = "yumemi-inc/android-engineer-codecheck",
    ownerIconUrl = Owner("https://avatars.githubusercontent.com/u/6687975?v=4"),
    language = "Kotlin",
    stargazersCount = 71,
    watchersCount = 71,
    forksCount = 3,
    openIssuesCount = 9
)
