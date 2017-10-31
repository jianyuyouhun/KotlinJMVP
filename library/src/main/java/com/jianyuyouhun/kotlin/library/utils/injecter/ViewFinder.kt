package com.jianyuyouhun.kotlin.library.utils.injecter

import android.app.Activity
import android.app.Dialog
import android.view.View

/**
 *
 * Created by wangyu on 2017/7/28.
 */
abstract class ViewFinder {
    companion object {
        fun create(activity: Activity): ViewFinder = ActivityViewFinder(activity)
        fun create(view: View): ViewFinder = ViewViewFinder(view)
        fun create(dialog: Dialog): ViewFinder = DialogViewFinder(dialog)
    }

    abstract fun bindView(id: Int): View
}

class ActivityViewFinder(val activity: Activity): ViewFinder() {
    override fun bindView(id: Int): View = activity.findViewById(id)
}

class ViewViewFinder(val view: View): ViewFinder() {
    override fun bindView(id: Int): View = view.findViewById(id)
}

class DialogViewFinder(val dialog: Dialog): ViewFinder() {
    override fun bindView(id: Int): View = dialog.findViewById(id)
}