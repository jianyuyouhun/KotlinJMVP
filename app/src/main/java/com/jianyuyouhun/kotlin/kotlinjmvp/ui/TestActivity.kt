package com.jianyuyouhun.kotlin.kotlinjmvp.ui

import android.os.Bundle
import android.widget.TextView
import com.jianyuyouhun.kotlin.kotlinjmvp.R
import com.jianyuyouhun.kotlin.kotlinjmvp.mvp.TestModel
import com.jianyuyouhun.kotlin.kotlinjmvp.mvp.TestPresenter
import com.jianyuyouhun.kotlin.kotlinjmvp.mvp.TestView
import com.jianyuyouhun.kotlin.library.app.BaseMVPActivity
import com.jianyuyouhun.kotlin.library.app.KTApp

/**
 *
 * Created by wangyu on 2017/7/26.
 */
class TestActivity: BaseMVPActivity<TestPresenter, TestModel>(), TestView {

    lateinit var textView: TextView
    override fun onSuccess(data: String) {
        textView.text = data
        showToast("数据获取成功")
    }

    override fun bindModelAndView(presenter: TestPresenter) {
        mPresenter.onBindModelView(mModel!!, this)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_test_mvp
    }

    override fun initModel(): TestModel? {
        return KTApp.mInstance.getKTModel(TestModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = findViewById(R.id.textView) as TextView
        mPresenter.start()
    }
}