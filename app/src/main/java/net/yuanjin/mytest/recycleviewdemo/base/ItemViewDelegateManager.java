package net.yuanjin.mytest.recycleviewdemo.base;

import android.support.v4.util.SparseArrayCompat;

/**
 *  itemView 委托管理器
 *  this is copy from https://github.com/hongyangAndroid/baseAdapter
 *  Created by WuZhanQiang on 2016/12/23.
 */

public class ItemViewDelegateManager<T> {

    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat();

    public int getItemViewDelegateCount(){
        return delegates.size();
    }

    /**
     * 添加 ItemView 委托
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate){
        int viewType = delegates.size();
        if (delegate != null){
            delegates.put(viewType, delegate);//key 为 postion+1
            viewType++;
        }
        return this;
    }

    /**
     * 根据 viewType 添加 ItemView 委托
     * @param viewType
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate){
        if (delegates.get(viewType) != null){
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                    + viewType
                    + ". Already registered ItemViewDelegate is "
                    + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    /**
     * 移除 ItemView 委托
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate){
        if (delegate == null){
            throw new NullPointerException("ItemViewDelegate is null");
        }

        int indexToRemove = delegates.indexOfValue(delegate);
        if (indexToRemove >= 0){
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(int itemType){
        int indexToRemove = delegates.indexOfKey(itemType);
        if (indexToRemove >= 0){
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 功能描述：返回指定位置 position 的 itemView 类型
     * @param item
     * @param position
     * @return
     */
    public int getItemViewType(T item, int position){
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--){
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType( item, position)){
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public void convert(ViewHolder holder, T item, int position){
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++){
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType( item, position)){
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    public ItemViewDelegate getItemViewDelegate(int viewType){
        return delegates.get(viewType);
    }

    public int getItemViewLayoutId(int viewType){
        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(ItemViewDelegate itemViewDelegate){
        return delegates.indexOfValue(itemViewDelegate);
    }
}
