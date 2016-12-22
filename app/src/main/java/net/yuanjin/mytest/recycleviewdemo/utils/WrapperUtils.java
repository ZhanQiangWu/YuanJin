package net.yuanjin.mytest.recycleviewdemo.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 *  装饰者工具
 *  Created by WuZhanQiang on 2016/12/22.
 */

public class WrapperUtils {

    /**
     * adapter 绑定 recyclerview
     */
    public static void onAttachedToRecyclerView(RecyclerView.Adapter innerAdapter,
                                                RecyclerView recyclerView,final SpanSizeCallback callback){

        innerAdapter.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            //设置每个item的跨度
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {//item 拥有的span数量
                    return callback.getSpanSize(gridLayoutManager,spanSizeLookup,position);
                }
            });
            //设置行/列的数量
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    public static void setFullSpan(RecyclerView.ViewHolder holder){
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams !=null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams ){
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            params.setFullSpan(true);
        }
    }

    public interface SpanSizeCallback{
        int getSpanSize(GridLayoutManager layoutManager,GridLayoutManager.SpanSizeLookup oldLookUp,int position);
    }
}
