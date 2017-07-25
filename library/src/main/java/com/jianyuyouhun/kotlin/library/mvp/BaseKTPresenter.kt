package com.jianyuyouhun.kotlin.library.mvp

import android.content.Context
import com.jianyuyouhun.kotlin.library.app.KTApp
import java.lang.ref.WeakReference

/**
 * Presenter基类
 * Created by wangyu on 2017/7/25.
 */
abstract class BaseKTPresenter<MajorManager : BaseKTModel, MajorView : BaseKTView> {
    var mModel: MajorManager? = null
    private var mView: MajorView? = null
    private var mViewRef: WeakReference<MajorView>? = null
    var context: Context? = null
    var isDestroy = false

    protected val TAG: String = BaseKTPresenter::class.java.simpleName

    fun onCreate(context: Context) {
        this.context = context
        isDestroy = false
    }

    fun onBindModelView(model: MajorManager, view: MajorView) {
        mViewRef = WeakReference(view)
        this.mView = view
        this.mModel = model
    }

    fun getKTView(): MajorView? {
        if (isAttach()) {
            return mViewRef!!.get()
        } else {
            return null
        }
    }

    fun isAttach(): Boolean {
        return mModel != null && mViewRef!!.get() != null
    }

    fun onDestroy() {
        if (mViewRef != null) {
            mViewRef!!.clear()
            mViewRef = null
        }
        isDestroy = true
    }
    /**
     * 获取辅助model
     */
    fun <MinorModel : BaseKTModel> getModel(model: Class<MinorModel>): MinorModel {
        return KTApp.mInstance!!.getKTModel(model)
    }
}