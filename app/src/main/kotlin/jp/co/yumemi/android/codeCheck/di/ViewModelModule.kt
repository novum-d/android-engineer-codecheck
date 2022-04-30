package jp.co.yumemi.android.codeCheck.di

import jp.co.yumemi.android.codeCheck.SearchRepositoryViewModel
import jp.co.yumemi.android.codeCheck.repository.SearchRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchRepositoryViewModel(get<SearchRepositoryImpl>()) }
}
