package net.yuanjin.mytest.recycleviewdemo.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import net.yuanjin.mytest.recycleviewdemo.base.ViewHolder;
import net.yuanjin.mytest.recycleviewdemo.utils.WrapperUtils;

/**
 *  加载更多装饰器
 *  Created by WuZhanQiang on 2016/12/23.
 */

public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;
    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper(RecyclerView.Adapter adapter){
        mInnerAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("mytest","--- LoadMoreWrapper  onCreateViewHolder --->");
        if (viewType == ITEM_TYPE_LOAD_MORE){
            ViewHolder holder;
            if (mLoadMoreView != null){
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            }else{
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("mytest","--- LoadMoreWrapper  onBindViewHolder ---> position: "+position);
        if (isShowLoadMore(position)){
            if (mOnLoadMoreListener != null){
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
       return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)){
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.i("mytest","--- LoadMoreWrapper  onAttachedToRecyclerView ---> ");
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {

            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookUp, int position) {
                if (isShowLoadMore(position)){
                    return layoutManager.getSpanCount();
                }
                if (oldLookUp != null){
                    return oldLookUp.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())){
            WrapperUtils.setFullSpan(holder);
        }
    }

    /**
     * 功能描述：判断是否已加载更多
     * @return
     */
    private boolean hasLoadMore(){
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    /**
     * 功能描述：判断是否再显示加载更多
     * @param position
     * @return
     */
    private boolean isShowLoadMore(int position){
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }


    public interface OnLoadMoreListener{
        void onLoadMoreRequested();
    }

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener){
        if (loadMoreListener != null)
        {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView){
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int layoutId){
        mLoadMoreLayoutId = layoutId;
        return this;
    }
}
