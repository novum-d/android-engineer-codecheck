package jp.co.yumemi.android.codeCheck.data.repository

import android.widget.TextView
import jp.co.yumemi.android.codeCheck.data.model.GitRepo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun createSearchCallbackFlow(textView: TextView): Flow<List<GitRepo>>
}
