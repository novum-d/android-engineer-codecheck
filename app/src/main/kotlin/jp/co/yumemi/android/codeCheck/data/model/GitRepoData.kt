package jp.co.yumemi.android.codeCheck.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * リポジトリの検索結果
 *
 * @property items Gitリポジトリリスト
 */
@Serializable
data class GitRepoList(
    val items: List<GitRepo>
) : java.io.Serializable

/**
 * Gitリポジトリ
 *
 * @property name リポジトリ名
 * @property owner オーナー
 * @property language 使用しているプログラミング言語
 * @property stargazersCount star数
 * @property watchersCount 視聴者数
 * @property forksCount fork数
 * @property openIssuesCount issue数
 * @property htmlUrl リポジトリのURL
 */
@Serializable
data class GitRepo(
    @SerialName("full_name")
    val name: String,
    val owner: Owner,
    val language: String?,
    @SerialName("stargazers_count")
    val stargazersCount: Long,
    @SerialName("watchers_count")
    val watchersCount: Long,
    @SerialName("forks_count")
    val forksCount: Long,
    @SerialName("open_issues_count")
    val openIssuesCount: Long,
    @SerialName("html_url")
    val htmlUrl: String
) : java.io.Serializable

/**
 * オーナー
 *
 * @property avatarUrl アバターURL
 */
@Serializable
data class Owner(
    @SerialName("avatar_url")
    val avatarUrl: String
) : java.io.Serializable
