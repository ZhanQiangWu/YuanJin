package net.yuanjin.test.recycleviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.OvershootInterpolator;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;
import net.yuanjin.widget.navigation.NavigationText;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 *  Created by WuZhanQiang on 2016/12/16.
 */

public class RecyclerViewActivity extends BasicActivity{

    private RecyclerView recyclerView;
    private List<String> mDatas;
    private NavigationText navigation;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initView();


    }

    private void initView() {

        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }

        initActionBar();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        //recyclerView.setLayoutManager(new GridLayoutManager(this,14,GridLayoutManager.HORIZONTAL,false));
        //recyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        //动画配置
        recyclerView.setItemAnimator(new DefaultItemAnimator());//自带默认动画
        //recyclerView.setItemAnimator(new SlideInUpAnimator());
        adapter=new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        //item点击监听
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this,mDatas.get(position)+" click" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this,mDatas.get(position)+" longclick" ,Toast.LENGTH_SHORT).show();
                adapter.removeData(position);
            }
        });



    }

    private void initActionBar() {
        if (navigation==null){
            navigation = new NavigationText(this);
            navigation.setTitle("RecycleView");
            navigation.setRightButton(R.mipmap.actionbar_add, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.addData(1,"hello"+(mDatas.size()+1));
                }
            });

            navigation.setRightButton2(R.mipmap.img_del, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.removeData(1);
                }
            });
        }
        this.setNavigation(navigation);
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

        private List<Integer> heightList;
        private OnItemClickListener mOnItemClickListener;

        RecyclerViewAdapter(){
            heightList = new ArrayList<Integer>();
            for (int i=0;i<mDatas.size();i++){
                heightList.add((int) (Math.random()*300+100));
            }
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.mOnItemClickListener = listener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(getLayoutInflater().inflate(R.layout.item_recycleview,parent,false));
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            //设置随机高度
            ViewGroup.LayoutParams params = holder.tv.getLayoutParams();
            params.height = heightList.get(position);
            holder.tv.setLayoutParams(params);

            holder.tv.setText(mDatas.get(position));

            //点击监听
            if (mOnItemClickListener!=null){
                holder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v,holder.getLayoutPosition());
                    }
                });

                holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onItemLongClick(v,holder.getLayoutPosition());
                        return false;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        void addData(int position,String desc){
            mDatas.add(position,desc);
            heightList.add(position,(int) (Math.random()*300+100));
            notifyItemInserted(position);
        }

        void removeData(int position){
            mDatas.remove(position);
            heightList.remove(position);
            notifyItemRemoved(position);
        }


        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tv;

            MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.id_recycler_num);
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view ,int position);
        void onItemLongClick(View view,int position);
    }
}
