package net.yuanjin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.yuanjin.R;
import net.yuanjin.mvp.login.view.LoginMVPActivity;
import net.yuanjin.mytest.recycleviewdemo.DividerItemDecoration;
import net.yuanjin.mytest.recycleviewdemo.RecyclerViewActivity;
import net.yuanjin.mytest.recycleviewdemo.base.ViewHolder;
import net.yuanjin.mytest.recycleviewdemo.sample.CommonAdapter;
import net.yuanjin.mytest.recycleviewdemo.sample.MultiItemRvActivity;
import net.yuanjin.mytest.recycleviewdemo.sample.MultiItemTypeAdapter;
import net.yuanjin.widget.navigation.NavigationText;

import java.util.ArrayList;
import java.util.List;

/**
 *  学习示例集
 *  Created by WuZhanQiang on 2016/12/23.
 */

public class MySamplesActivity extends BasicActivity{

    private List<SampleItem> sampleDatas;

    private NavigationText navigation;
    private CommonAdapter<SampleItem> adapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recyclerview);

        initNavigation();

        //初始化 Sample 的标题和类
        initTitleAndClass();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        adapter = new CommonAdapter<SampleItem>(this,R.layout.item_recycleview, sampleDatas) {

            @Override
            protected void convert(ViewHolder holder, SampleItem sampleItem, int position) {
                holder.setText(R.id.id_recycler_text,sampleItem.getTitle());
            }

        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(new Intent(MySamplesActivity.this, sampleDatas.get(position).getActivity()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void initTitleAndClass() {
        sampleDatas = new ArrayList<SampleItem>();
        sampleDatas.add(new SampleItem("MVP", LoginMVPActivity.class));
        sampleDatas.add(new SampleItem("RecyclerView", RecyclerViewActivity.class));
        sampleDatas.add(new SampleItem("MultitemRvActivity", MultiItemRvActivity.class));
    }

    private void initNavigation() {
        if (navigation == null){
            navigation = new NavigationText(this);
            navigation.setTitle("MySamples");
        }
        setNavigation(navigation);
    }

    class SampleItem{
        String title;
        Class activity;

        public SampleItem(String title,Class activity){
            this.title = title;
            this.activity = activity;
        }

        public String getTitle() {
            return title;
        }

        public Class getActivity() {
            return activity;
        }
    }
}
