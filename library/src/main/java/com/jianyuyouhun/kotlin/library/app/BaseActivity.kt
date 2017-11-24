package com.jianyuyouhun.kotlin.library.app

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.provider.Settings
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.jianyuyouhun.kotlin.library.R
import com.jianyuyouhun.kotlin.library.app.broadcast.LightBroadCast
import com.jianyuyouhun.kotlin.library.mvp.common.ThemeModel
import com.jianyuyouhun.kotlin.library.utils.*
import com.jianyuyouhun.kotlin.library.utils.injecter.injectModel
import com.jianyuyouhun.kotlin.library.utils.injecter.injectView

/**
 * Activity基类
 * Created by wangyu on 2017/7/25.
 */
abstract class BaseActivity: AppCompatActivity() {
    var mProgressDialog: ProgressDialog? = null
    private var mIsDestroy = true
    private var mIsFinish = true
    private var mLastClickTime = System.currentTimeMillis()
    companion object {
        var IS_DEBUG_MODE = KTApp.isDebug
        fun dipToPx(dip: Float): Int = CommonUtils.dipToPx(KTApp.mInstance as Context, dip)
    }

    private val onGlobalMsgReceiveListener = {msg: Message? ->
        if (msg?.what == ThemeModel.MSG_WHAT_ALL_ACTIVITY_CLOSE_SELF) {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIsDestroy = false
        mIsFinish = false
        initTheme()
        val layoutId = getLayoutResId()
        if (layoutId != 0) {
            setContentView(getLayoutResId())
        } else {
            val view = buildLayoutView()
            if (view != null) {
                setContentView(view)
            }
        }
        injectView(this)
        injectModel(this)
        LightBroadCast.instance.addOnGlobalMsgReceiveListener(onGlobalMsgReceiveListener)
    }

    open fun initTheme() {
        val themeId = KTApp.mInstance.getKTModel(ThemeModel::class.java)?.getCurrentTheme()
        if (themeId == -1) {
            return
        }
        setTheme(themeId!!)
    }

    @LayoutRes abstract fun getLayoutResId(): Int

    @Deprecated("已使用buildLayoutView替代", ReplaceWith("super.setContentView(view)", "android.support.v7.app.AppCompatActivity"))
    override fun setContentView(view: View?) = super.setContentView(view)

    @Deprecated("已使用getLayoutResId替代", ReplaceWith("super.setContentView(layoutResID)", "android.support.v7.app.AppCompatActivity"))
    override fun setContentView(layoutResID: Int) = super.setContentView(layoutResID)

    /**
     * 不想用layoutId时重写此方法返回view,layoutId返回0
     */
    open fun buildLayoutView(): View? = null

    fun showToast(@StringRes msgId: Int) = showToast(getString(msgId))

    fun showToast(msg: String) {
        if (mIsDestroy) return
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun getContext(): Context = this

    override fun finish() {
        super.finish()
        mIsFinish = true
    }

    override fun onDestroy() {
        super.onDestroy()
        mIsDestroy = true
        LightBroadCast.instance.removeOnGlobalMsgReceiveListener(onGlobalMsgReceiveListener)
    }

    /**
     * 显示进度对话框
     */
    fun showProgressDialog() {
        if (mIsDestroy) return
        initProgressDialog()
        if (!mProgressDialog!!.isShowing) {
            mProgressDialog!!.show()
        }
    }

    /**
     * 关闭进度对话框
     */
    fun dismissProgressDialog() {
        if (mIsDestroy) return
        if (mProgressDialog == null) return
        if (mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

    protected open fun initProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(getContext())
            mProgressDialog!!.setCancelable(false)
        }
    }

    /**
     * 是否在500毫秒内连续点击
     */
    @Synchronized fun isFastDoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        if (mLastClickTime > time) {
            mLastClickTime = time
            return false
        }
        if (time - mLastClickTime < 500) {
            return true
        }
        mLastClickTime = time
        return false
    }

    /**
     * 是否在前台运行
     */
    fun isAppOnForeground(): Boolean = !CommonUtils.isRunAtBackground(this)

    /**
     * 启动activity
     */
    fun startActivity(cls: Class<out Activity>) = startActivity(Intent(this, cls))

    fun getActivity(): BaseActivity = this

    /**
     * 启动设置页面
     */
    fun startSystemSettingActivity(requestCode: Int) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        try {
            startActivityForResult(intent, requestCode)
        } catch (ex: Exception) {
            ex.printStackTrace()
            showToast(getString(R.string.setting_class_not_found))
        }
    }

    /**
     * 以类名打印e日志
     */
    fun logE(msg: String) = lgE(javaClass.simpleName, msg)

    /**
     * 以类名打印i日志
     */
    fun logI(msg: String) = lgI(javaClass.simpleName, msg)

    /**
     * 以类名打印w日志
     */
    fun logW(msg: String) = lgW(javaClass.simpleName, msg)

    /**
     * 以类名打印d日志
     */
    fun logD(msg: String) = lgD(javaClass.simpleName, msg)
}