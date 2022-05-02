package jp.co.yumemi.android.codeCheck.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import jp.co.yumemi.android.codeCheck.data.repository.SearchRepositoryImpl
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val codeCheckAppModule = module {
    single { SearchRepositoryImpl(client, androidContext()) }
}

private val client = HttpClient(Android) {
    install(HttpTimeout)
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
    engine {
        connectTimeout = 60_000
        socketTimeout = 60_000
    }
}
