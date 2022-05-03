package jp.co.yumemi.android.codeCheck.di

import jp.co.yumemi.android.codeCheck.app.GitRepoSearchViewModel
import jp.co.yumemi.android.codeCheck.data.repository.SearchRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GitRepoSearchViewModel(get<SearchRepositoryImpl>()) }
}
