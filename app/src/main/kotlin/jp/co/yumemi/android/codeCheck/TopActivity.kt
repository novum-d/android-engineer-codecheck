/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.co.yumemi.android.codeCheck.di.codeCheckAppModule
import jp.co.yumemi.android.codeCheck.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin

class TopActivity : AppCompatActivity(R.layout.activity_top) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalContext.getOrNull() ?: startKoin {
            androidContext(this@TopActivity)
            modules(listOf(viewModelModule, codeCheckAppModule))
        }
    }
}
