package com.jianyuyouhun.kotlin.kotlinjmvp.util

import android.view.View
import android.widget.LinearLayout

/**
 * 扩展类
 * Created by wangyu on 2017/11/24.
 */

fun LinearLayout.getChildren() : List<View> = (0 until childCount).map { getChildAt(it) }