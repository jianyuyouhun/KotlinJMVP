package com.jianyuyouhun.kotlin.kotlinjmvp.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jianyuyouhun.kotlin.library.utils.injecter.ViewInjector;

import java.util.ArrayList;
import java.util.List;


/**
 * 模仿recyclerview的adapter做的封装
 * Created by wangyu on 2017-4-24
 */
public abstract class SimpleBaseAdapter<Data, VH extends SimpleBaseAdapter.ViewHolder> extends BaseAdapter {
    protected Context context;
    private List<Data> dataList = new ArrayList<>();

    public SimpleBaseAdapter(Context context) {
        this.context = context;
    }

    /**
     * 添加要显示的数据到末尾 注意：调用本方法设置数据，listView不需要再调用：notifyDataSetChanged
     *
     * @param datas 数据列表
     */
    public void addData(List<Data> datas) {
        this.dataList.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 删除某条数据
     *
     * @param position
     */
    public void deleteData(int position) {
        this.dataList.remove(position);
        notifyDataSetChanged();
    }


    /**
     * 获取要所有的数据
     *
     * @return 适配器数据源
     */
    public List<Data> getData() {
        return new ArrayList<>(dataList);
    }

    /**
     * 设置要显示的数据，注意：调用本方法设置数据，listView不需要再调用：notifyDataSetChanged
     *
     * @param datas 数据
     */
    public void setData(List<Data> datas) {
        this.dataList.clear();
        addData(datas);
    }

    public void addToLast(Data data) {
        dataList.add(data);
        notifyDataSetChanged();
    }

    public void addToFirst(Data data) {
        dataList.add(0, data);
        notifyDataSetChanged();
    }

    /**
     * 获取适配器的最后一项，如果适配器大小等于0，将返回null
     */
    public Data getLastItem() {
        int count = getCount();
        if (count == 0) {
            return null;
        } else {
            return getItem(count - 1);
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Data getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH viewHolder;
        if (convertView == null) {
            viewHolder = onNewViewHolder();
            convertView = LayoutInflater.from(context).inflate(getLayoutId(), parent, false);
            viewHolder.setItemView(convertView);
            viewHolder.initView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }

        Data data = getItem(position);
        bindView(viewHolder, data, position);
        return convertView;
    }

    /**
     * 获得资源文件的ID
     *
     * @return 资源文件id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void bindView(VH viewHolder, Data data, int position);

    @NonNull
    protected abstract VH onNewViewHolder();

    public static class ViewHolder {
        private View itemView;

        public View getItemView() {
            return itemView;
        }

        public void setItemView(View itemView) {
            this.itemView = itemView;
        }

        public void initView() {
            ViewInjector.Companion.inject(this, itemView);
        }
    }
}
