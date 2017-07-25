package com.jianyuyouhun.kotlin.kotlinjmvp

import android.os.Bundle
import com.jianyuyouhun.kotlin.library.app.BaseActivity

class MainActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
