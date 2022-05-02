/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.codeCheck.repository.SearchRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

class SearchGitRepoViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    /** 最終検索日時 */
    private val _lastSearchDate = MutableLiveData<Date>()
    val lastSearchDate: LiveData<Date> get() = _lastSearchDate

    /** Git Repositoryリスト */
    private val _repositories = MutableLiveData<List<GitRepo>>()
    val repositories: LiveData<List<GitRepo>> get() = _repositories

    @FlowPreview
    fun onSearch(textView: TextView) {
        viewModelScope.launch {
            searchRepository.createSearchCallbackFlow(textView)
                // .debounce(10_00)
                .collect {
                    _repositories.value = it
                }
            _lastSearchDate.value = Date()
        }
    }
}

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
    val openIssuesCount: Long
) : java.io.Serializable

@Serializable
data class Owner(
    @SerialName("avatar_url")
    val avatarUrl: String
) : java.io.Serializable
