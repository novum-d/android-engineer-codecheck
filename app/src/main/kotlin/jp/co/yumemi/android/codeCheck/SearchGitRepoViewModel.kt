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

class SearchGitRepoViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    /** 最終検索日時 */
    private var _lastSearchDate = MutableLiveData<Date>()
    val lastSearchDate: LiveData<Date> get() = _lastSearchDate

    /** Git Repositoryリスト */
    val repositories: LiveData<List<GitRepo>> get() = _repositories
    private var _repositories = MutableLiveData<List<GitRepo>>()

    fun searchGitRepositories(keyword: String) {
        viewModelScope.launch {
            _repositories.value = searchRepository.searchGitRepositories(keyword)
            _lastSearchDate.value = Date()
        }
    }
}

@Parcelize
data class GitRepo(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable
