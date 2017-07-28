package com.jianyuyouhun.kotlin.kotlinjmvp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.jianyuyouhun.kotlin.kotlinjmvp.R
import com.jianyuyouhun.kotlin.library.app.BaseActivity
import com.jianyuyouhun.kotlin.library.utils.injecter.FindViewByID

class MainActivity : BaseActivity() {

    @FindViewByID(R.id.textView)
    lateinit var textView: TextView
    @FindViewByID(R.id.test_mvp_btn)
    lateinit var testMvpBtn: Button

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView.text="hello Kotlin"
        testMvpBtn.setOnClickListener {
            startActivity(TestActivity::class.java)
        }
    }


}
