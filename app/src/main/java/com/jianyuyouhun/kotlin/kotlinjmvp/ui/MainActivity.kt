package com.jianyuyouhun.kotlin.kotlinjmvp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.jianyuyouhun.kotlin.kotlinjmvp.R
import com.jianyuyouhun.kotlin.library.app.BaseActivity

class MainActivity : BaseActivity() {

    lateinit var textView: TextView
    lateinit var testMvpBtn: Button

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = findViewById(R.id.textView) as TextView
        testMvpBtn = findViewById(R.id.test_mvp_btn) as Button
        textView.text="hello Kotlin"
        testMvpBtn.setOnClickListener {
            startActivity(TestActivity::class.java)
        }
    }


}
