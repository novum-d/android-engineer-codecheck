/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.di.launchKoin

class TopActivity : AppCompatActivity(R.layout.activity_top) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchKoin(this@TopActivity)
    }
}
