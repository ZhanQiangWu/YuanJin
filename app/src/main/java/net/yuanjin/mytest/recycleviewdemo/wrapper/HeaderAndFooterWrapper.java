package net.yuanjin.mytest.recycleviewdemo.wrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import net.yuanjin.mytest.recycleviewdemo.base.ViewHolder;
import net.yuanjin.mytest.recycleviewdemo.utils.WrapperUtils;


/**
 *  首部、尾部装饰器
 *  Created by WuZhanQiang on 2016/12/22.
 */

public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();//<key,value> => <viewType,View>
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter){
        mInnerAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("mytest","--- HeaderAndFooterWrapper  onCreateViewHolder --->");
        if (mHeaderViews.get(viewType) != null){
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
            return holder;
        }else if (mFootViews.get(viewType) != null){
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)){
            return mHeaderViews.keyAt(position);
        }else if (isFooterViewPos(position)){
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("mytest","--- HeaderAndFooterWrapper  onBindViewHolder ---> position: "+position);
        if (isHeaderViewPos(position)){
            return;
        }

        if (isFooterViewPos(position)){
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.i("mytest","--- HeaderAndFooterWrapper  onAttachedToRecyclerView ---> ");
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            //获得item的span数量
            @Override
            public int getSpanSize(GridLayoutManager layoutManager,
                                   GridLayoutManager.SpanSizeLookup oldLookUp, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null){//该item 为 header
                    return layoutManager.getSpanCount();
                }else if (mFootViews.get(viewType) != null){//该item 为 footView
                    return layoutManager.getSpanCount();
                }

                if (oldLookUp != null ){
                    return oldLookUp.getSpanSize(position);
                }
                return 1;
            }
        });

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        Log.i("mytest","--- HeaderAndFooterWrapper  onViewAttachedToWindow ---> position: "+position);

        //若为 首部或尾部 则填充满（类似于合并单元格）
        if (isFooterViewPos(position) || isHeaderViewPos(position)){
            WrapperUtils.setFullSpan(holder);
        }
    }

    /**
     * 添加首部 view
     * @param view
     */
    public void addHeaderView(View view){
        mHeaderViews.put(mHeaderViews.size()+BASE_ITEM_TYPE_HEADER,view);
    }

    /**
     * 功能描述：添加底部 view
     * @param view
     */
    public void addFootView(View view){
        mFootViews.put(mFootViews.size()+BASE_ITEM_TYPE_FOOTER,view);
    }

    /**
     * 功能描述：判断特定位置 position 是否位于首部区域
     * @param position
     * @return
     */
    private boolean isHeaderViewPos(int position){
        return position < getHeadersCount();
    }

    /**
     * 功能描述：获取首部数量
     * @return
     */
    public int getHeadersCount(){
        return mHeaderViews.size();
    }

    /**
     * 功能描述：判断特定位置 position 是否位于尾部区域
     * @param position
     * @return
     */
    private boolean isFooterViewPos(int position){
        return position >= getHeadersCount() + getRealItemCount();
    }

    /**
     * 功能描述：获取有效数据数量（除去首、尾部分）
     * @return
     */
    private int getRealItemCount(){
        return mInnerAdapter.getItemCount();
    }

    /**
     * 功能描述：获取尾部数量
     * @return
     */
    public int getFootersCount(){
        return mFootViews.size();
    }
}
