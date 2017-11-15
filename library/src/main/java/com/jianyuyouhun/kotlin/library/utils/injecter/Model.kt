package com.jianyuyouhun.kotlin.library.utils.injecter

/**
 * 注解获取Model
 * Created by wangyu on 2017/11/15.
 */

@Deprecated("Kotlin中请使用by bindModel<T>(Model.class), Java中依然可用")
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Model