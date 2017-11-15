package com.jianyuyouhun.kotlin.library.utils.injecter

/**
 * 注解获取view
 * Created by wangyu on 2017/7/28.
 */

@Deprecated("Kotlin中请使用by bindView<T>(R.id.x), Java中依然可用")
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class FindViewByID(val value: Int)
