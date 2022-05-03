/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.codeCheck.data.model.GitRepo
import jp.co.yumemi.android.codeCheck.data.repository.SearchRepository
import kotlinx.coroutines.launch
import java.util.*

/**
 * リポジトリ検索ビューモデル
 *
 * @property searchRepository
 */
class GitRepoSearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    /** 最終検索日時 */
    private val _lastSearchDate = MutableLiveData<Date>()
    val lastSearchDate: LiveData<Date> get() = _lastSearchDate

    /** Git Repositoryリスト */
    private val _repositories = MutableLiveData<List<GitRepo>>()
    val repositories: LiveData<List<GitRepo>> get() = _repositories

    /** 検索フィールドのアニメーションの状態 */
    private val _expanded = MutableLiveData<Boolean>()
    val expanded: LiveData<Boolean> get() = _expanded

    init {
        _expanded.value = true
    }

    /**
     * 検索
     *
     * @param name リポジトリ名
     */
    fun onSearch(name: String) {
        viewModelScope.launch {
            _repositories.value = searchRepository.requestGitRepositories(name)
        }
    }

    /**
     * 検索フィールドのアニメーションの状態を切り替える
     *
     */
    fun switchExpand() {
        _expanded.value?.let {
            _expanded.value = !it
        }
    }
}
