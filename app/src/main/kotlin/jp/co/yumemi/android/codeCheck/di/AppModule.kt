package jp.co.yumemi.android.codeCheck.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import jp.co.yumemi.android.codeCheck.repository.SearchRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val codeCheckAppModule = module {
    single { SearchRepositoryImpl(client, androidContext()) }
}

val client = HttpClient(Android) {
    install(HttpTimeout)
}