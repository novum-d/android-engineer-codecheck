package jp.co.yumemi.android.codeCheck.data.repository

import jp.co.yumemi.android.codeCheck.data.model.GitRepo

interface SearchRepository {
    suspend fun requestGitRepositories(name: String): List<GitRepo>
}
