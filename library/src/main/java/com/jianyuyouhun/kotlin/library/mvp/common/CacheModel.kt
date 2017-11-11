package com.jianyuyouhun.kotlin.library.mvp.common

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

import com.jianyuyouhun.kotlin.library.mvp.BaseKTModel
import com.jianyuyouhun.kotlin.library.utils.json.JsonUtil
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

/**
 * 缓存model
 * Created by wangyu on 2017/8/11.
 */

class CacheModel : BaseKTModel() {

    private lateinit var sp: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    private var uId: String? = null

    companion object {
        private val KT_APP_CACHE_NAME = "kt_app_cache"
        private val GUEST_ID = "1001"
    }

    override fun onModelCreate(app: Application) {
        sp = app.getSharedPreferences(KT_APP_CACHE_NAME, Context.MODE_PRIVATE)
        spEditor = sp.edit()
        spEditor.apply()
        onUserLogout()
    }

    fun onUserLogin(userId: String) {
        this.uId = userId
    }

    fun onUserLogout() {
        this.uId = GUEST_ID
    }

    /**
     * 获取当前缓存的key
     *
     * @param key   原key
     * @return  拼接的key
     */
    private fun getCurrentKey(key: String): String = key + "_" + uId

    /**
     * 缓存实体
     * @param key       key
     * @param any       Any
     */
    fun putObject(key: String, any: Any) {
        val jsonObject = JsonUtil.toJSONObject(any)
        putString(key, jsonObject.toString())
    }

    /**
     * 取出缓存实体
     *
     * @param key       key
     * @param cls       cls类型
     * @param <T>       泛型
     * @return  返回实体
     */
    fun <T> getObject(key: String, cls: Class<T>): T? {
        val jsonStr = getString(key, "")
        if ("" == jsonStr) {
            return null
        }
        var t: T?
        try {
            t = JsonUtil.toObject(JSONObject(jsonStr), cls)
        } catch (e: JSONException) {
            e.printStackTrace()
            t = null
        }

        return t
    }

    /**
     * 缓存实体列表
     *
     * @param key       key
     * @param list      list
     * @param <T>       泛型
     */
    fun <T> putList(key: String, list: List<T>?) {
        if (list == null) {
            return
        }
        val jsonArray = JSONArray()
        list
                .mapNotNull { JsonUtil.toJSONObject(it) }
                .forEach { jsonArray.put(it) }
        putString(key, jsonArray.toString())
    }

    /**
     * 取出缓存实体列表
     *
     * @param key       key
     * @param cls       实体类
     * @param <T>       泛型
     * @return  list
     */
    fun <T> getList(key: String, cls: Class<T>): List<T> {
        var list: List<T> = ArrayList()
        val jsonStr = getString(key, "")
        if ("" == jsonStr) {
            return list
        }

        try {
            val jsonArray = JSONArray(jsonStr)
            list = JsonUtil.toList(jsonArray, cls)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }

    /**
     * SP中写入String类型value
     *
     * @param key   键
     * @param value 值
     */
    fun putString(key: String, value: String) = spEditor.putString(getCurrentKey(key), value).apply()

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getString(key: String, defaultValue: String? = null): String = sp.getString(getCurrentKey(key), defaultValue)

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     */
    fun putInt(key: String, value: Int) = spEditor.putInt(getCurrentKey(key), value).apply()

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getInt(key: String, defaultValue: Int = -1): Int = sp.getInt(getCurrentKey(key), defaultValue)

    /**
     * SP中写入long类型value
     *
     * @param key   键
     * @param value 值
     */
    fun putLong(key: String, value: Long) = spEditor.putLong(getCurrentKey(key), value).apply()

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getLong(key: String, defaultValue: Long = -1L): Long = sp.getLong(getCurrentKey(key), defaultValue)

    /**
     * SP中写入float类型value
     *
     * @param key   键
     * @param value 值
     */
    fun putFloat(key: String, value: Float) = spEditor.putFloat(getCurrentKey(key), value).apply()

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getFloat(key: String, defaultValue: Float = -1f): Float = sp.getFloat(getCurrentKey(key), defaultValue)

    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     */
    fun putBoolean(key: String, value: Boolean) = spEditor.putBoolean(getCurrentKey(key), value).apply()

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean = sp.getBoolean(getCurrentKey(key), defaultValue)

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    val all: Map<String, *>
        get() = sp.all

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    fun remove(key: String) = spEditor.remove(getCurrentKey(key)).apply()

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return `true`: 存在 `false`: 不存在
     */
    operator fun contains(key: String): Boolean = sp.contains(getCurrentKey(key))

    /**
     *
     * SP中清除所有数据
     */
    fun clear() = spEditor.clear().apply()
}