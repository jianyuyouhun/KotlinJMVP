package com.jianyuyouhun.kotlin.library.utils.injecter

import android.app.Activity
import android.app.Dialog
import android.view.View
import java.lang.reflect.Field

/**
 * 注解绑定工厂
 * Created by wangyu on 2017/7/28.
 */

private fun create(activity: Activity): ViewFinder = ViewFinder.ActivityViewFinder(activity)
private fun create(view: View?): ViewFinder = ViewFinder.ViewViewFinder(view)
private fun create(dialog: Dialog): ViewFinder = ViewFinder.DialogViewFinder(dialog)

fun injectView(target: Any, view: View?) = injectView(target, create(view))

fun injectView(activity: Activity) = injectView(activity, create(activity))

fun injectView(dialog: Dialog) = injectView(dialog, create(dialog))

private fun injectView(target: Any?, viewFinder: ViewFinder?) {
    if (target == null || viewFinder == null) {
        throw NullPointerException()
    }
    var cls: Class<*> = target::class.java
    while (cls != Object::class.java && cls != Activity::class.java && cls != View::class.java) {
        val declaredFields: Array<Field> = cls.declaredFields
        for (field: Field in declaredFields) {
            if (!field.isAccessible) field.isAccessible = true
            val findViewById: FindViewByID? = field.getAnnotation(FindViewByID::class.java)
            if (findViewById == null) {
                continue
            } else {
                val id = findViewById.value
                val view: View? = viewFinder.bindView(id)
                if (view == null) {
                    val msg = field.name + " 根据ID不能查找到对应的View，请检查XML资源文件中的id属性是否等于注解的ID"
                    throw ViewInjectException(msg)
                } else if (!field.type.isInstance(view)) {
                    val msg = field.name + " 类型匹配错误，java类型：" +
                            field.type.simpleName + ", View在XML中申明的类型：" +
                            view::class.java.simpleName + "，请检查XML中的控件类型和java代码类型是否匹配"
                    throw ViewInjectException(msg)
                } else {
                    try {
                        field.set(target, view)
                    } catch (ex: IllegalAccessException) {
                        ex.printStackTrace()
                    } catch (ex: IllegalArgumentException) {
                        ex.printStackTrace()
                    }
                }
            }
        }
        cls = cls::class.java.superclass
    }
}

class ViewInjectException(msg: String) : RuntimeException(msg)