package com.jianyuyouhun.kotlin.library.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jianyuyouhun.kotlin.library.utils.injecter.injectModel
import com.jianyuyouhun.kotlin.library.utils.injecter.injectView

/**
 * Fragment基类
 * Created by wangyu on 2017/7/25.
 */
abstract class BaseFragment: Fragment() {
    private var isDestroy = true
    var mInsertDt = System.currentTimeMillis()
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isDestroy = false
    }

    @Deprecated("请重写onCreateView(rootView: View?, parent: ViewGroup?, savedInstanceState: Bundle?)",
            ReplaceWith("super.onCreateView(inflater, container, savedInstanceState)",
                    "android.support.v4.app.Fragment"))
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = getLayoutResId()
        val view = if (layoutId == 0) {
            buildLayoutView()
        } else {
            inflater!!.inflate(layoutId, container, false)
        }
        injectView(this, view)
        injectModel(this)
        onCreateView(view, container, savedInstanceState)
        return view
    }

    open fun buildLayoutView(): View? = null

    abstract fun getLayoutResId(): Int

    abstract fun onCreateView(rootView: View?, parent: ViewGroup?, savedInstanceState: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        isDestroy = true
    }

    fun getBaseActivity(): BaseActivity = activity as BaseActivity

    fun showProgressDialog() = getBaseActivity().showProgressDialog()

    fun dismissProgressDialog() = getBaseActivity().dismissProgressDialog()

    /**
     * 启动activity
     */
    fun startActivity(cls: Class<out Activity>) = startActivity(Intent(getBaseActivity(), cls))
}