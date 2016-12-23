package net.yuanjin.mytest.recycleviewdemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import net.yuanjin.mytest.recycleviewdemo.MyCommonAdapter;
import net.yuanjin.mytest.recycleviewdemo.base.ViewHolder;

import java.util.List;

/**
 *  Created by wzq on 2016/12/20.
 */

public abstract  class MultiItemMyAdapter<T> extends MyCommonAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemMyAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> multiItemTypeSupport){
        super(context,-1,datas);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position,mDatas.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext,parent,layoutId);
        return viewHolder;
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    public interface MultiItemTypeSupport<T>{

        //通过itemType获取 layout
        int getLayoutId(int itemType);

        //根据位置确定 itemType 类型
        int getItemViewType(int position, T t);
    }
}
