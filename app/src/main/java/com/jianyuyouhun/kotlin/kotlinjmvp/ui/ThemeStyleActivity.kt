package com.jianyuyouhun.kotlin.kotlinjmvp.ui

import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.jianyuyouhun.kotlin.kotlinjmvp.R
import com.jianyuyouhun.kotlin.kotlinjmvp.util.AppUtils
import com.jianyuyouhun.kotlin.library.app.BaseActivity
import com.jianyuyouhun.kotlin.library.mvp.common.ThemeInfo
import com.jianyuyouhun.kotlin.library.mvp.common.ThemeModel
import com.jianyuyouhun.kotlin.library.utils.SimpleBaseAdapter
import com.jianyuyouhun.kotlin.library.utils.proxy.bindModel
import com.jianyuyouhun.kotlin.library.utils.proxy.bindView
import kotterknife.bindView

/**
 * 主题选择
 * Created by wangyu on 2017/8/11.
 */

class ThemeStyleActivity : BaseActivity() {

    private val listView by bindView<ListView>(R.id.list_view)

    private lateinit var themeAdapter: ThemeAdapter

    private val themeModel by bindModel(ThemeModel::class.java)

    override fun getLayoutResId(): Int = R.layout.activity_theme_style

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeAdapter = ThemeAdapter(getContext())
        themeAdapter.onItemClick = {data ->
            if (data.idDefault) {
                showToast("你已经使用了该主题")
            } else {
                themeModel.resetCurrentTheme(data.value)
                showToast("设置成功, 正在重启app")
                AppUtils.restartAPP(getContext(), 1000)
            }
        }
        listView.adapter = themeAdapter
        refreshData()
        judgeThemeSources()
    }

    private fun refreshData() {
        themeAdapter.setInfoList(themeModel.themeList)
    }

    private fun judgeThemeSources() {
        if (themeAdapter.count == 0) {
            themeModel.addTheme(ThemeInfo("夜间模式", R.style.AppBaseNightTheme, false))
            themeModel.addTheme(ThemeInfo("普通模式", R.style.AppBaseTheme, true))
            themeModel.addTheme(ThemeInfo("夜间模式-无标题栏", R.style.AppBaseNightThemeNoActionBar, false))
            themeModel.addTheme(ThemeInfo("普通模式-无标题栏", R.style.AppBaseThemeNoActionBar, false))
        }
        refreshData()
    }

    class ThemeAdapter(context: Context): SimpleBaseAdapter<ThemeInfo, ThemeAdapter.ViewHolder>(context) {

        var onItemClick: ((themeInfo: ThemeInfo) -> Unit)? = null

        override fun getLayoutId(): Int = R.layout.list_theme_item

        override fun bindView(viewHolder: ViewHolder, info: ThemeInfo, position: Int) {
            val showContent = info.name + if (info.idDefault) "(当前主题)" else ""
            viewHolder.name.text = showContent
            viewHolder.name.setOnClickListener { onItemClick?.invoke(info) }
        }

        class ViewHolder: SimpleBaseAdapter.ViewHolder() {
            val name by bindView<TextView>(R.id.theme_name)
        }
    }
}
