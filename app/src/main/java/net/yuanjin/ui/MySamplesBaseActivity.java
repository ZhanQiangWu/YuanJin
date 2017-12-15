package net.yuanjin.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.yuanjin.R;
import net.yuanjin.mytest.mvp.login.view.LoginMVPActivity;
import net.yuanjin.mytest.mvp.simples.MVPActivity1;
import net.yuanjin.mytest.ConnectWithJS.JSActivity;
import net.yuanjin.mytest.customwidget.paint_canvas.PaintCanvasActivity;
import net.yuanjin.mytest.picassodemo.PicassoSampleActivity;
import net.yuanjin.mytest.picassodemo.imageloader.XtionImageLoaderDemo;
import net.yuanjin.mytest.recycleviewdemo.DividerItemDecoration;
import net.yuanjin.mytest.recycleviewdemo.RecyclerViewActivity;
import net.yuanjin.mytest.recycleviewdemo.base.ViewHolder;
import net.yuanjin.mytest.recycleviewdemo.sample.CommonAdapter;
import net.yuanjin.mytest.recycleviewdemo.sample.MultiItemRvActivity;
import net.yuanjin.mytest.recycleviewdemo.sample.MultiItemTypeAdapter;
import net.yuanjin.mytest.recycleviewdemo.sample.RecyclerViewActivity2;
import net.yuanjin.mytest.retrofitdemo.RetrofitDemoActivity;
import net.yuanjin.mytest.rxjavademo.RxJavaDemoActivity;
import net.yuanjin.mytest.tencent_xinge.TencentXinGeTestActivity;
import net.yuanjin.mytest.viewstudy.VelocityTrackerActivity;
import net.yuanjin.mytest.viewstudy.ViewSamplesActivity;
import net.yuanjin.widget.navigation.NavigationText;
import net.yuanjin.widgetlib.photolib.Sample_Photolib;

import java.util.ArrayList;
import java.util.List;

/**
 *  学习示例集
 *  Created by WuZhanQiang on 2016/12/23.
 */

public class MySamplesBaseActivity extends BasicActivity{

    private List<SampleItem> sampleDatas;

    private NavigationText navigation;
    private CommonAdapter<SampleItem> adapter;
    private RecyclerView recyclerView;
    protected List<Class> classList = new ArrayList<>();


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recyclerview);
        initNavigation();

        //初始化 Sample 的标题和类
        initSampleDatas();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                startActivity(new Intent(MySamplesBaseActivity.this, sampleDatas.get(position).getActivity()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);

    }

    protected void requestPermission() {
        // TODO Auto-generated method stub
        // 6.0以上系统才可以判断权限

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            // 进入设置系统应用权限界面
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(intent);
            return;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
            // 进入设置系统应用权限界面
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(intent);
            return;
        }
        return;
    }

    private void initSampleDatas() {
        initClassDatas();

        sampleDatas = new ArrayList<SampleItem>();

        for (Class c : classList) {
            sampleDatas.add(new SampleItem(c.getSimpleName(), c));
        }
    }

    protected void initClassDatas() {
        classList.add(LoginMVPActivity.class);
        classList.add(LoginMVPActivity.class);
        classList.add(RecyclerViewActivity.class);
        classList.add(MultiItemRvActivity.class);
        classList.add(RecyclerViewActivity2.class);
        classList.add(RxJavaDemoActivity.class);
        classList.add(PicassoSampleActivity.class);
        classList.add(XtionImageLoaderDemo.class);
        classList.add(Sample_Photolib.class);
        classList.add(TencentXinGeTestActivity.class);
        classList.add(PaintCanvasActivity.class);
        classList.add(JSActivity.class);
        classList.add(MVPActivity1.class);
        classList.add(RetrofitDemoActivity.class);
        classList.add(ViewSamplesActivity.class);
    }

    private void initNavigation() {
        if (navigation == null){
            navigation = new NavigationText(this);
            navigation.setTitle("MySamples");
        }
        setNavigation(navigation);
    }

    static class SampleItem{
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
