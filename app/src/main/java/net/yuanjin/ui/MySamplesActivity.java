package net.yuanjin.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.yuanjin.R;
import net.yuanjin.mvp.login.view.LoginMVPActivity;
import net.yuanjin.mvp.simples.MVPActivity1;
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
import net.yuanjin.mytest.rxjavademo.RxJavaDemoActivity;
import net.yuanjin.mytest.tencent_xinge.TencentXinGeTestActivity;
import net.yuanjin.widget.navigation.NavigationText;
import net.yuanjin.widgetlib.photolib.Sample_Photolib;

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

//        Log.i("TPushReceiver", "YuanJinApplication 账号解绑");
//        XGPushManager.registerPush(getApplicationContext(),"*");
//        Log.i("TPushReceiver", "--------------- 反注册操作 ------------");
//        XGPushManager.unregisterPush(getApplicationContext());


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

//        if (NotificationsUtils.isNotificationEnabled(this)){
//            Toast.makeText(this,"有权限",Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this,"没有权限",Toast.LENGTH_SHORT).show();
//            requestPermission();
//        }

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

    private void initTitleAndClass() {
        Class[] classArray = new Class[]{LoginMVPActivity.class,RecyclerViewActivity.class,
                MultiItemRvActivity.class,RecyclerViewActivity2.class,
                RxJavaDemoActivity.class,PicassoSampleActivity.class,
                XtionImageLoaderDemo.class,Sample_Photolib.class,
                TencentXinGeTestActivity.class,PaintCanvasActivity.class,
                JSActivity.class, MVPActivity1.class
        };

        sampleDatas = new ArrayList<SampleItem>();

        for (Class c : classArray) {
            sampleDatas.add(new SampleItem(c.getSimpleName(), c));
        }
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
