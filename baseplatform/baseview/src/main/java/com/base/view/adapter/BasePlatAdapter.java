package com.base.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 所有的 adapter的父类
 */
public abstract class BasePlatAdapter<T> extends android.widget.BaseAdapter {
    protected Context context;
    protected List<T> listData;
    protected LayoutInflater mLayoutInflater;

    public BasePlatAdapter(Context context) {
        this.context = context;
        listData = new ArrayList<T>();
        mLayoutInflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        if (listData == null) {
            return 0;
        }
        return listData.size();
    }

    @Override
    public T getItem(int position) {
        if (listData == null) {
            return null;
        }
        return listData.get(position);
    }

    /**
     * 获取集合
     *
     * @return
     */
    public List<T> getItems() {
        return listData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(int location, T idata) {
        listData.add(location, idata);
        notifyDataSetChanged();
    }

    public void append(T data) {
        listData.add(data);
        notifyDataSetChanged();
    }

    public void removeByPosition(int position) {
        listData.remove(position);
        notifyDataSetChanged();
    }

    public void removeByObject(String picPath) {
        listData.remove(picPath);
        notifyDataSetChanged();
    }

    public void setRes(Collection<T> list) {
        listData.clear();
        listData.addAll(list);
        notifyDataSetChanged();
    }

    protected <T extends View> T r2v(View view, int resId) {
        return (T) view.findViewById(resId);
    }


}
