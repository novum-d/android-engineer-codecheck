package jp.co.yumemi.android.codeCheck.repository

import android.content.Context
import android.os.SystemClock
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
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
import jp.co.yumemi.android.codeCheck.GitRepo
import jp.co.yumemi.android.codeCheck.GitRepoList
import jp.co.yumemi.android.codeCheck.data.config.HttpRoutes
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class SearchRepositoryImpl(
    private val client: HttpClient,
    private val context: Context
) : SearchRepository {

    override suspend fun createSearchCallbackFlow(textView: TextView): Flow<List<GitRepo>> = callbackFlow {
        var lastClickTime: Long = 0

        // データ更新を通知するリスナー
        val listener = OnEditorActionListener { editText, action, _ ->
            // 検索buttonを押す間隔を1秒以上にする
            if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                return@OnEditorActionListener false
            }
            lastClickTime = SystemClock.elapsedRealtime()
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                when (val keyword = editText.text.toString()) {

                    // 何も入力されていない場合、入力を促すToastを表示
                    "" -> Toast.makeText(context, "キーワードが入力されていません", Toast.LENGTH_SHORT).show()

                    // Git repositoryを検索
                    else -> launch {
                        val repositories = requestGitRepositories(keyword)
                        trySend(repositories)
                    }
                }
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        }

        // リスナーを登録
        textView.setOnEditorActionListener(listener)

        // Flowがキャンセルされるまで待ち、キャンセルされたらリスナーを解除
        awaitClose { textView.setOnEditorActionListener(null) }
    }

    private suspend fun requestGitRepositories(keyword: String): List<GitRepo> {

        // GithubApiにaccessし、HttpResponseを受け取る
        val response: HttpResponse = try {
            client.get(HttpRoutes.GIT_HUB_API) {
                timeout {
                    requestTimeoutMillis = 6000 // タイムアウトするまでミリ秒
                }
                contentType(ContentType.Application.Json)
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", keyword)
            }
        } catch (e: Throwable) {
            Toast.makeText(context, "現在サービスがご利用いただけません", Toast.LENGTH_SHORT).show()
            null
        } ?: return emptyList()

        return response.body<GitRepoList>().items
        //     language = context.getString(R.string.written_language, language),
    }
}
