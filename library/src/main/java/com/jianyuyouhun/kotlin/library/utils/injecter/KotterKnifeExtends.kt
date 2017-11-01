package com.jianyuyouhun.kotlin.library.utils.injecter

import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import com.jianyuyouhun.kotlin.library.utils.SimpleBaseAdapter.ViewHolder

/**
 *  kotterKnife扩展类，为SimpleBaseAdapter增加延时加载策略
 * Created by wangyu on 2017/10/31.
 */

fun <V : View> ViewHolder.bindView(id: Int)
        : ReadOnlyProperty<com.jianyuyouhun.kotlin.library.utils.SimpleBaseAdapter.ViewHolder, V> = required(id, viewFinder)

fun <V : View> ViewHolder.bindOptionalView(id: Int)
        : ReadOnlyProperty<com.jianyuyouhun.kotlin.library.utils.SimpleBaseAdapter.ViewHolder, V?> = optional(id, viewFinder)

fun <V : View> ViewHolder.bindViews(vararg ids: Int)
        : ReadOnlyProperty<com.jianyuyouhun.kotlin.library.utils.SimpleBaseAdapter.ViewHolder, List<V>> = required(ids, viewFinder)

fun <V : View> ViewHolder.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<com.jianyuyouhun.kotlin.library.utils.SimpleBaseAdapter.ViewHolder, List<V>> = optional(ids, viewFinder)

private val ViewHolder.viewFinder: ViewHolder.(Int) -> View?
    get() = { itemView.findViewById(it) }

private fun viewNotFound(id:Int, desc: KProperty<*>): Nothing =
        throw IllegalStateException("View ID $id for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(id: Int, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> t.finder(id) as V? ?: viewNotFound(id, desc) }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(id: Int, finder: T.(Int) -> View?)
        = Lazy { t: T, desc ->  t.finder(id) as V? }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(ids: IntArray, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> ids.map { t.finder(it) as V? ?: viewNotFound(it, desc) } }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(ids: IntArray, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> ids.map { t.finder(it) as V? }.filterNotNull() }

// Like Kotlin's lazy delegate but the initializer gets the target and metadata passed to it
private class Lazy<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {
    private object EMPTY
    private var value: Any? = EMPTY

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }
}