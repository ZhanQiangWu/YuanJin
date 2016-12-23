package net.yuanjin.mytest.recycleviewdemo.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import net.yuanjin.R;
import net.yuanjin.mytest.recycleviewdemo.been.ChatMessage;
import net.yuanjin.mytest.recycleviewdemo.wrapper.LoadMoreWrapper;
import net.yuanjin.ui.BasicActivity;
import net.yuanjin.widget.navigation.NavigationText;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by WuZhanQiang on 2016/12/23.
 */

public class MultiItemRvActivity extends BasicActivity{


    private RecyclerView mRecyclerView;
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<ChatMessage> mDatas = new ArrayList<>();

    private NavigationText navigation;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recyclerview);
        initNavigation();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatas.addAll(ChatMessage.MOCK_DATAS);

        ChatAdapterForRv adapter = new ChatAdapterForRv(this,mDatas);

        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(this).inflate(R.layout.default_loading, mRecyclerView, false));
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean coming = Math.random() > 0.5;
                        ChatMessage msg = null;
                        msg = new ChatMessage(coming ? R.mipmap.renma : R.mipmap.xiaohei, coming ? "人马" : "xiaohei", "where are you " + mDatas.size(),
                                null, coming);
                        mDatas.add(msg);
                        mLoadMoreWrapper.notifyDataSetChanged();
                    }
                },3000);
            }
        });

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(MultiItemRvActivity.this, "Click:" + position , Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(MultiItemRvActivity.this, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mRecyclerView.setAdapter(mLoadMoreWrapper);
    }

    private void initNavigation() {
        if (navigation == null){
            navigation = new NavigationText(this);
            navigation.setTitle(this.getLocalClassName());
            setNavigation(navigation);
        }
    }
}
