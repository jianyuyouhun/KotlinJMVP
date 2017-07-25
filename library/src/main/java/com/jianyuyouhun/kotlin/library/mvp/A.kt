package com.jianyuyouhun.kotlin.library.mvp

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

/**
 * Created by wangyu on 2017/7/25.
 */

class A : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TextView(this).setTextColor(Color.RED)
    }
}
