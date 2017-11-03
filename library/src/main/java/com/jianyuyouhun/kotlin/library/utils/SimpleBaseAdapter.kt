package com.jianyuyouhun.kotlin.library.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.lang.Exception
import java.lang.reflect.ParameterizedType


/**
 * 模仿recyclerView的adapter做的封装
 * Created by wangyu on 2017-11-03
 */
abstract class SimpleBaseAdapter<Info, in VH : SimpleBaseAdapter.ViewHolder>(val context: Context) : BaseAdapter() {
    private val infoList = ArrayList<Info>()

    /**
     * 添加要显示的数据到末尾 注意：调用本方法设置数据，listView不需要再调用：notifyDataSetChanged
     *
     * @param infoList 数据列表
     */
    fun addInfoList(infoList: List<Info>) {
        this.infoList.addAll(infoList)
        notifyDataSetChanged()
    }

    /**
     * 删除某条数据
     *
     * @param position
     */
    fun deleteInfo(position: Int) {
        this.infoList.removeAt(position)
        notifyDataSetChanged()
    }


    /**
     * 获取要所有的数据
     *
     * @return 适配器数据源
     */
    fun getInfoList(): List<Info> {
        return ArrayList(infoList)
    }

    /**
     * 设置要显示的数据，注意：调用本方法设置数据，listView不需要再调用：notifyDataSetChanged
     *
     * @param infoList 数据
     */
    fun setInfoList(infoList: List<Info>) {
        this.infoList.clear()
        addInfoList(infoList)
    }

    fun addToLast(data: Info) {
        infoList.add(data)
        notifyDataSetChanged()
    }

    fun addToFirst(data: Info) {
        infoList.add(0, data)
        notifyDataSetChanged()
    }

    fun addToPos(data: Info, pos: Int) {
        infoList.add(pos, data)
        notifyDataSetChanged()
    }

    /**
     * 获取适配器的最后一项，如果适配器大小等于0，将返回null
     */
    fun getLastItem(): Info? {
        val count = count
        return if (count == 0) {
            null
        } else {
            getItem(count - 1)
        }
    }

    override fun getCount(): Int = infoList.size

    override fun getItem(position: Int): Info = infoList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @Suppress("UNCHECKED_CAST")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder: VH
        var itemView = convertView
        if (itemView == null) {
            val viewHolderCls: Class<VH> = (javaClass.genericSuperclass as ParameterizedType)
                    .actualTypeArguments[1] as Class<VH>
            try {
                viewHolder = viewHolderCls.newInstance()
            } catch (e : Exception) {
                e.printStackTrace()
                throw IllegalAccessException("初始化ViewHolder失败： 请确保" + viewHolderCls.simpleName + "为static class 并拥有无参构造函数")
            }
            itemView = LayoutInflater.from(context).inflate(getLayoutId(), parent, false)
            viewHolder.itemView = itemView
            itemView?.tag = viewHolder
        } else {
            viewHolder = itemView.tag as VH
        }
        val info: Info = getItem(position)
        viewHolder.adapterPosition = position
        bindView(viewHolder, info, position)
        return itemView
    }

    abstract fun getLayoutId(): Int

    abstract fun bindView(viewHolder: VH, info: Info, position: Int)

    open class ViewHolder {
        var adapterPosition: Int = -1
        lateinit var itemView: View
    }
}