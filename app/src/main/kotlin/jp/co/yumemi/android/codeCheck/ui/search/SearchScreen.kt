package jp.co.yumemi.android.codeCheck.ui.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.codeCheck.data.model.GitRepo
import jp.co.yumemi.android.codeCheck.data.model.Owner
import jp.co.yumemi.android.codeCheck.di.launchKoin
import jp.co.yumemi.android.codeCheck.ui.components.SearchBody
import jp.co.yumemi.android.codeCheck.ui.components.SearchHeader
import jp.co.yumemi.android.codeCheck.ui.theme.CodeCheckTheme

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SearchScreen(
    repositories: List<GitRepo>,
    navigateToDetail: (GitRepo) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        SearchHeader()
        SearchBody(
            repositories = repositories,
            navigateToDetail = navigateToDetail,
            modifier = Modifier.weight(1f)
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchScreenPreview() {
    // プレビューするコンポーネントツリーにkoinでdiを行っている場合は、koinを起動する必要がある
    launchKoin(LocalContext.current)
    CodeCheckTheme {
        SearchScreen(repositories = repositories, navigateToDetail = {})
    }
}

val repositories = listOf(
    GitRepo(
        name = "dcyuki/yumemi_bot",
        ownerIconUrl = Owner(avatarUrl = "https://avatars.githubusercontent.com/u/37479458?v=4"),
        language = "JavaScript",
        stargazersCount = 25,
        watchersCount = 25,
        forksCount = 4,
        openIssuesCount = 0
    ),
    GitRepo(
        name = "yumemi-inc/ios-engineer-codecheck",
        ownerIconUrl = Owner(avatarUrl = "https://avatars.githubusercontent.com/u/6687975?v=4"),
        language = "Swift",
        stargazersCount = 75,
        watchersCount = 75,
        forksCount = 5,
        openIssuesCount = 9
    ),
    GitRepo(
        name = "Kaito-Dogi/android-intern-assignment-yumemi",
        ownerIconUrl = Owner(avatarUrl = "https://avatars.githubusercontent.com/u/49048577?v=4"),
        language = "Kotlin",
        stargazersCount = 0,
        watchersCount = 0,
        forksCount = 4,
        openIssuesCount = 1
    )
)
