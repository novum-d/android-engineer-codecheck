package jp.co.yumemi.android.codeCheck.repository

import jp.co.yumemi.android.codeCheck.Repository

interface SearchRepository {
    suspend fun searchGitRepositories(inputText: String): List<Repository>
}
