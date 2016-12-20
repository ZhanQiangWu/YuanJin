package net.yuanjin.test.recycleviewdemo.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 *  Created by WuZhanQiang on 2016/12/20.
 */

public class ViewHolder extends RecyclerView.ViewHolder{

    private Context mcontext;
    private View mConvertView;
    private SparseArray<View> mViews;

    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mcontext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    public static ViewHolder get(Context context,ViewGroup parent,int layoutId){

    }

    /**
     * 通过 viewId 获取控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View>T getView(int viewId){

    }

}
