package jp.co.yumemi.android.codeCheck.repository

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.GitRepo
import jp.co.yumemi.android.codeCheck.data.config.HttpRoutes
import org.json.JSONArray
import org.json.JSONObject

class SearchRepositoryImpl(
    private val client: HttpClient,
    private val context: Context
) : SearchRepository {

    override suspend fun searchGitRepositories(inputText: String): List<GitRepo> {

        // GithubApiにaccessし、HttpResponseを受け取る
        val response: HttpResponse = client.get(HttpRoutes.GIT_REPO_API) {
            header("Accept", "application/vnd.github.v3+json")
            parameter("q", inputText)
        }

        // HttpResponseをJSON配列に変換
        val jsonBody = JSONObject(response.body<String>())
        val jsonItems = jsonBody.optJSONArray("items") ?: JSONArray()
        val items = mutableListOf<GitRepo>()

        // Git repositoryのlistを作成
        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.optJSONObject(i)
            val name = jsonItem.optString("full_name")
            val ownerIconUrl = jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: ""
            val language = jsonItem.optString("language")
            val stargazersCount = jsonItem.optLong("stargazers_count")
            val watchersCount = jsonItem.optLong("watchers_count")
            val forksCount = jsonItem.optLong("forks_conut")
            val openIssuesCount = jsonItem.optLong("open_issues_count")
            val repository = GitRepo(
                name = name,
                ownerIconUrl = ownerIconUrl,
                language = context.getString(R.string.written_language, language),
                stargazersCount = stargazersCount,
                watchersCount = watchersCount,
                forksCount = forksCount,
                openIssuesCount = openIssuesCount
            )
            items.add(repository)
        }
        return items.toList()
    }
}
