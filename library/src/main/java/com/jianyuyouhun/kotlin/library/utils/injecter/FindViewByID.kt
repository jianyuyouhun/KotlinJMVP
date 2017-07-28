package com.jianyuyouhun.kotlin.library.utils.injecter

/**
 * 注解获取view
 * Created by wangyu on 2017/7/28.
 */

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class FindViewByID(val value: Int)
