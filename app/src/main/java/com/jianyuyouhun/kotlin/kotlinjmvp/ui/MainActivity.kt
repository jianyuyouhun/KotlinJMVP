package com.jianyuyouhun.kotlin.kotlinjmvp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.jianyuyouhun.kotlin.kotlinjmvp.R
import com.jianyuyouhun.kotlin.library.app.BaseActivity
import kotterknife.bindView

class MainActivity : BaseActivity() {

    private val textView by bindView<TextView>(R.id.textView)
    private val testMvpBtn by bindView<Button>(R.id.test_mvp_btn)
    private val testSetThemeBtn by bindView<Button>(R.id.test_theme_set)
    private val buttonContainer by bindView<LinearLayout>(R.id.button_container)

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView.text="hello Kotlin"
        testMvpBtn.setOnClickListener {
            startActivity(TestActivity::class.java)
        }
        testSetThemeBtn.setOnClickListener {
            startActivity(ThemeStyleActivity::class.java)
        }
        (0 until buttonContainer.childCount)
                .map { buttonContainer.getChildAt(it) }
                .map { logD(it.javaClass.simpleName) }
    }
}
