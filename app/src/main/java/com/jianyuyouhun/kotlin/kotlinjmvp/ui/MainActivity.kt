package com.jianyuyouhun.kotlin.kotlinjmvp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.jianyuyouhun.kotlin.kotlinjmvp.R
import com.jianyuyouhun.kotlin.library.app.BaseActivity
import com.jianyuyouhun.kotlin.library.utils.injecter.bindView

class MainActivity : BaseActivity() {

    val textView by bindView<TextView>(R.id.textView)
    val testMvpBtn by bindView<Button>(R.id.test_mvp_btn)
    val testSetThemeBtn by bindView<Button>(R.id.test_theme_set)

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView.text="hello Kotlin"
        testMvpBtn.setOnClickListener {
            startActivity(TestActivity::class.java)
        }
        testSetThemeBtn.setOnClickListener {
            startActivity(ThemeStyleActivity::class.java)
        }
    }

}
