package jp.co.yumemi.android.codeCheck.repository

import jp.co.yumemi.android.codeCheck.GitRepo

interface SearchRepository {
    suspend fun searchGitRepositories(inputText: String): List<GitRepo>
}
