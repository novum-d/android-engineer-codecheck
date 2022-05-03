package jp.co.yumemi.android.codeCheck.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitRepoList(
    val items: List<GitRepo>
) : java.io.Serializable

@Serializable
data class GitRepo(
    @SerialName("full_name")
    val name: String,
    @SerialName("owner")
    val ownerIconUrl: Owner,
    @SerialName("language")
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

@Serializable
data class Owner(
    @SerialName("avatar_url")
    val avatarUrl: String
) : java.io.Serializable
