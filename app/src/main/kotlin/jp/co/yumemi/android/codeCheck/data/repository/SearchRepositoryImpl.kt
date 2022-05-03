package jp.co.yumemi.android.codeCheck.data.repository

import android.content.Context
import android.widget.Toast
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.data.enumType.HttpRoutes
import jp.co.yumemi.android.codeCheck.data.model.GitRepo
import jp.co.yumemi.android.codeCheck.data.model.GitRepoList

class SearchRepositoryImpl(
    private val client: HttpClient,
    private val context: Context
) : SearchRepository {

    override suspend fun requestGitRepositories(name: String): List<GitRepo> {

        // GithubApiにアクセスし、レスポンスを受け取る
        val response: HttpResponse = try {
            client.get(HttpRoutes.GIT_HUB_API) {
                timeout {
                    requestTimeoutMillis = 6000 // タイムアウトするまでミリ秒
                }
                contentType(ContentType.Application.Json)
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", name)
            }
        } catch (e: Throwable) {
            Toast.makeText(context, context.getString(R.string.service_is_not_available), Toast.LENGTH_SHORT).show()
            null
        } ?: return emptyList()

        return response.body<GitRepoList>().items
    }
}
