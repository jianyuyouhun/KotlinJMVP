package com.jianyuyouhun.kotlin.library.mvp.common

import android.app.Application
import android.os.Message
import com.jianyuyouhun.kotlin.library.app.broadcast.LightBroadCast
import com.jianyuyouhun.kotlin.library.mvp.BaseKTModel
import com.jianyuyouhun.kotlin.library.utils.proxy.bindModel

/**
 * 主题管理器
 * Created by wangyu on 2017/8/11.
 */
class ThemeModel : BaseKTModel() {
    companion object {
        val MSG_WHAT_CHANGE_APP_THEME = -101
        val MSG_WHAT_ALL_ACTIVITY_CLOSE_SELF = -102
    }

    private var defaultThemeId = -1
    private val KEY_THEME_LIST = "key_theme_list"

    val handler = LightBroadCast.getInstance()
    var themeList = ArrayList<ThemeInfo>()
    val cacheModel by bindModel(CacheModel::class.java)

    override fun onModelCreate(app: Application) {
        themeList = cacheModel.getList(KEY_THEME_LIST, ThemeInfo::class.java)
                as ArrayList<ThemeInfo>
        initDefaultTheme()
    }

    private fun initDefaultTheme() {
        for (info in themeList) {
            if (info.idDefault) {
                defaultThemeId = info.value
                break
            }
        }
    }

    fun getCurrentTheme(): Int {
        return defaultThemeId
    }

    fun addTheme(theme: ThemeInfo) {
        themeList.add(theme)
        synchronizeThemes()
    }

    fun removeTheme(theme: ThemeInfo) {
        for (info in themeList) {
            if (info.value == theme.value) {
                themeList.remove(info)
                break
            }
        }
        synchronizeThemes()
    }

    private fun synchronizeThemes() {
        cacheModel.putList(KEY_THEME_LIST, themeList)
    }

    fun resetCurrentTheme(themeId: Int) {
        defaultThemeId = themeId
        for (themeInfo in themeList) {
            themeInfo.idDefault = themeInfo.value == themeId
        }
        synchronizeThemes()
        notifyThemeChanged(defaultThemeId)
    }

    private fun notifyThemeChanged(defaultThemeId: Int) {
        val message = Message.obtain()
        message.what = MSG_WHAT_CHANGE_APP_THEME
        message.obj = defaultThemeId
        handler.sendMessage(message)
    }
}

data class ThemeInfo(var name: String, var value: Int, var idDefault: Boolean)