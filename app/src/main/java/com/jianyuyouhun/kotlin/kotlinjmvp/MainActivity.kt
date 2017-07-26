package com.jianyuyouhun.kotlin.kotlinjmvp

import android.os.Bundle
import android.widget.TextView
import com.jianyuyouhun.kotlin.library.app.BaseActivity

class MainActivity : BaseActivity() {

    var textView: TextView? = null

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = findViewById(R.id.textView) as TextView
        textView!!.text="hello Kotlin"
    }


}
