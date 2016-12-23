package net.yuanjin.mytest.recycleviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.yuanjin.mytest.recycleviewdemo.base.ViewHolder;

import java.util.List;

/**
 *  Created by zhan on 2016/12/20.
 */

public abstract  class MyCommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>{

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public MyCommonAdapter(Context context, int layoutId, List<T> datas){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext,parent,mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.updatePosition();
        convert(holder,mDatas.get(position));
    }

    public abstract void convert(ViewHolder viewHolder,T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
