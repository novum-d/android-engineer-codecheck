/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck.app

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.*
import jp.co.yumemi.android.codeCheck.data.model.GitRepo
import jp.co.yumemi.android.codeCheck.data.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchGitRepoViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    /** 最終検索日時 */
    private val _lastSearchDate = MutableLiveData<Date>()
    val lastSearchDate: LiveData<Date> get() = _lastSearchDate

    /** Git Repositoryリスト */
    private val _repositories = MutableLiveData<List<GitRepo>>()
    val repositories: LiveData<List<GitRepo>> get() = _repositories

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

