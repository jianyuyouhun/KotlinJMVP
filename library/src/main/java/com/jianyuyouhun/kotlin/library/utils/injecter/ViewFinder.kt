package com.jianyuyouhun.kotlin.library.utils.injecter

import android.app.Activity
import android.app.Dialog
import android.view.View

/**
 *
 * Created by wangyu on 2017/7/28.
 */
abstract class ViewFinder {

    abstract fun bindView(id: Int): View?

    class ActivityViewFinder(private val activity: Activity) : ViewFinder() {
        override fun bindView(id: Int): View? = activity.findViewById(id)
    }

    class ViewViewFinder(private val view: View?) : ViewFinder() {
        override fun bindView(id: Int): View? = view?.findViewById(id)
    }

    class DialogViewFinder(private val dialog: Dialog) : ViewFinder() {
        override fun bindView(id: Int): View? = dialog.findViewById(id)
    }
}