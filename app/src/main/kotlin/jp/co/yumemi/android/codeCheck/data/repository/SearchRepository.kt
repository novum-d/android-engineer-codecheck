package jp.co.yumemi.android.codeCheck.data.repository

import jp.co.yumemi.android.codeCheck.data.model.GitRepo

/**
 * 検索リポジトリ
 *
 */
interface SearchRepository {
    /**
     * Gitのリポジトリリストをリクエスト
     *
     * @param name リポジトリ名
     * @return Gitリポジトリリスト
     */
    suspend fun requestGitRepositories(name: String): List<GitRepo>
}
