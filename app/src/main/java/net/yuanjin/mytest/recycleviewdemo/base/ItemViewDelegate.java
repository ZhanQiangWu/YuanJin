package net.yuanjin.mytest.recycleviewdemo.base;

/**
 *  this is copy from https://github.com/hongyangAndroid/baseAdapter
 *  Created by WuZhanQiang on 2016/12/23.
 */

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);
}
