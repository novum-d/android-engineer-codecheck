/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.*
import jp.co.yumemi.android.codeCheck.repository.SearchRepository
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class SearchRepositoryViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var _lastSearchDate = MutableLiveData<Date>()
    val lastSearchDate: LiveData<Date> get() = _lastSearchDate

    fun searchGitRepositories(keyword: String): List<Repository> {
        var repositories = listOf<Repository>()
        viewModelScope.launch {
            repositories = searchRepository.searchGitRepositories(keyword)
            _lastSearchDate.value = Date()
        }
        return repositories
    }
}

@Parcelize
data class Repository(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable
