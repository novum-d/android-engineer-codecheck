/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import androidx.appcompat.app.AppCompatActivity
import java.util.Date

class TopActivity : AppCompatActivity(R.layout.activity_top) {

    companion object {
        lateinit var lastSearchDate: Date
    }
}
