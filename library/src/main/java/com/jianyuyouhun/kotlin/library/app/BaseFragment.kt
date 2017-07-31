package com.jianyuyouhun.kotlin.library.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jianyuyouhun.kotlin.library.utils.injecter.ViewInjector

/**
 * Fragment基类
 * Created by wangyu on 2017/7/25.
 */
abstract class BaseFragment: Fragment() {
    private var isDestroy = true
    private var mInsertDt = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isDestroy = false
    }

    @Deprecated("", ReplaceWith("super.onCreateView(inflater, container, savedInstanceState)", "android.support.v4.app.Fragment"))
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View?
        val layoutId = getLayoutResId()
        if (layoutId == 0) {
            view = buildLayoutView()
        } else {
            view = inflater!!.inflate(layoutId, container, false)
        }

        ViewInjector.inject(this, view!!)
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

    fun getInsertDt(): Long = mInsertDt

    fun showProgressDialog() = getBaseActivity().showProgressDialog()

    fun dismissProgressDialog() = getBaseActivity().dismissProgressDialog()

    /**
     * 启动activity
     */
    fun startActivity(cls: Class<out Activity>) = startActivity(Intent(getBaseActivity(), cls))
}