package net.yuanjin.mytest.recycleviewdemo.sample;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.yuanjin.R;
import net.yuanjin.mytest.recycleviewdemo.CommonAdapter;
import net.yuanjin.mytest.recycleviewdemo.DividerItemDecoration;
import net.yuanjin.mytest.recycleviewdemo.base.ViewHolder;
import net.yuanjin.ui.BasicActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by WuZhanQiang on 2016/12/22.
 */

public class RecyclerViewActivity2 extends BasicActivity{

    private RecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<>();
    private CommonAdapter<String> mAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initDatas();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CommonAdapter<String>(this,R.layout.item_recycleview2,mDatas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.id_recycler_num, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());

            }
        };
        initHeaderAndFooter();




    }

    private void initHeaderAndFooter() {

    }

    private void initDatas()
    {
        for (int i = 'A'; i <= 'z'; i++)
        {
            mDatas.add((char) i + "");
        }
    }
}
