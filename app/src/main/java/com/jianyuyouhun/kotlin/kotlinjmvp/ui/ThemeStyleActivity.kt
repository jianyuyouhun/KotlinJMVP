package com.jianyuyouhun.kotlin.kotlinjmvp.ui

import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.jianyuyouhun.kotlin.kotlinjmvp.R
import com.jianyuyouhun.kotlin.kotlinjmvp.adapter.SimpleBaseAdapter
import com.jianyuyouhun.kotlin.kotlinjmvp.app.App
import com.jianyuyouhun.kotlin.kotlinjmvp.util.AppUtils
import com.jianyuyouhun.kotlin.library.app.BaseActivity
import com.jianyuyouhun.kotlin.library.mvp.common.ThemeModel
import com.jianyuyouhun.kotlin.library.utils.injecter.FindViewByID

/**
 * 主题选择
 * Created by wangyu on 2017/8/11.
 */

class ThemeStyleActivity : BaseActivity() {

    @FindViewByID(R.id.list_view)
    lateinit var listView: ListView

    lateinit var themeAdapter: ThemeAdapter

    var themeModel: ThemeModel = App.getInstance().getKTModel(ThemeModel::class.java)

    override fun getLayoutResId(): Int {
        return R.layout.activity_theme_style
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeAdapter = ThemeAdapter(getContext())
        themeAdapter.setOnItemClickListener { data ->
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
        themeAdapter.data = themeModel.themeList
    }

    private fun judgeThemeSources() {
        if (themeAdapter.count == 0) {
            themeModel.addTheme(ThemeModel.ThemeInfo("夜间模式", R.style.AppBaseNightTheme, false))
            themeModel.addTheme(ThemeModel.ThemeInfo("普通模式", R.style.AppBaseTheme, true))
            themeModel.addTheme(ThemeModel.ThemeInfo("夜间模式-无标题栏", R.style.AppBaseNightThemeNoActionBar, false))
            themeModel.addTheme(ThemeModel.ThemeInfo("普通模式-无标题栏", R.style.AppBaseThemeNoActionBar, false))
        }
        refreshData()
    }

    class ThemeAdapter(context: Context): SimpleBaseAdapter<ThemeModel.ThemeInfo, ThemeAdapter.ViewHolder>(context) {

        var onItemClick: ((themeInfo: ThemeModel.ThemeInfo) -> Unit)? = null

        override fun getLayoutId(): Int = R.layout.list_theme_item

        override fun bindView(viewHolder: ViewHolder?, data: ThemeModel.ThemeInfo?, position: Int) {
            val holder = viewHolder as ViewHolder
            holder.name.text = data!!.name + if (data.idDefault) "(当前主题)" else ""
            holder.name.setOnClickListener {
                if (onItemClick != null) {
                    onItemClick!!.invoke(data)
                }
            }
        }

        override fun onNewViewHolder(): ViewHolder = ViewHolder()

        fun setOnItemClickListener(onItemClick: (themeInfo: ThemeModel.ThemeInfo) -> Unit) {
            this.onItemClick = onItemClick
        }

        class ViewHolder: SimpleBaseAdapter.ViewHolder() {
            @FindViewByID(R.id.theme_name)
            lateinit var name: TextView
        }
    }
}
