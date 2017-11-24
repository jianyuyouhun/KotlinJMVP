package com.jianyuyouhun.kotlin.library.mvp

import android.content.Context
import com.jianyuyouhun.kotlin.library.utils.lgD
import java.lang.ref.WeakReference

/**
 * Presenter基类
 * Created by wangyu on 2017/7/25.
 */
abstract class BaseKTPresenter<MajorManager : BaseKTModel, MajorView : BaseKTView> {

    var mModel: MajorManager? = null
        private set
    private var mView: MajorView? = null
    private var mViewRef: WeakReference<MajorView>? = null
    lateinit var context: Context
        private set
    var isDestroy = false
        private set

    lateinit var TAG: String

    open fun onCreate(context: Context) {
        this.context = context
        TAG = this::class.java.simpleName
        isDestroy = false
        lgD(TAG, "onCreate")
    }

    fun onBindModelView(model: MajorManager, view: MajorView) {
        mViewRef = WeakReference(view)
        this.mView = view
        this.mModel = model
    }

    fun getKTView(): MajorView? = if (isAttach()) mViewRef!!.get() else null

    fun isAttach(): Boolean = mModel != null && mViewRef!!.get() != null

    open fun onDestroy() {
        if (mViewRef != null) {
            mViewRef!!.clear()
            mViewRef = null
        }
        isDestroy = true
    }

}