package jp.co.yumemi.android.codeCheck.di

import jp.co.yumemi.android.codeCheck.app.SearchGitRepoViewModel
import jp.co.yumemi.android.codeCheck.data.repository.SearchRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchGitRepoViewModel(get<SearchRepositoryImpl>()) }
}
