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

class ActivityViewFinder(activity: Activity): ViewFinder() {

    private var mActivity: Activity = activity

    override fun bindView(id: Int): View = mActivity.findViewById(id)
}

class ViewViewFinder(view: View): ViewFinder() {

    private var mView: View = view
    override fun bindView(id: Int): View = mView.findViewById(id)
}

class DialogViewFinder(dialog: Dialog): ViewFinder() {
    private var mDialog: Dialog = dialog

    override fun bindView(id: Int): View = mDialog.findViewById(id)
}