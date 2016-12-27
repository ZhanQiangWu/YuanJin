package net.yuanjin.mytest.picassodemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.yuanjin.R;
import net.yuanjin.mytest.recycleviewdemo.DividerItemDecoration;
import net.yuanjin.ui.BasicActivity;
import net.yuanjin.widget.navigation.NavigationText;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by WuZhanQiang on 2016/12/27.
 */

public class PicassoSampleActivity extends BasicActivity{

    private RecyclerView recyclerView;
    private NavigationText navigation;
    private MyAdapter adapter;
    private List<String> imgUrls;
    private List<String> imgText;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picassosample);

        initActionBar();
        initImgDatas();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_picasso);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }


    private void initActionBar() {
        if (navigation == null){
            navigation = new NavigationText(this);
            navigation.setTitle("PicassoSample");
        }
        setNavigation(navigation);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(getLayoutInflater().inflate(R.layout.item_list_detail_picasso,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.text.setText(imgText.get(position));

            Picasso.with(PicassoSampleActivity.this)
                    .load(imgUrls.get(position))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_error)
                    .resizeDimen(R.dimen.list_detail_image_size,R.dimen.list_detail_image_size)
                    .centerInside()
                    .tag(PicassoSampleActivity.this)
                    .into(holder.image);
        }

        @Override
        public int getItemCount() {
            return imgText.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView text;

            public MyViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.photo);
                text = (TextView) itemView.findViewById(R.id.url);
            }
        }

    }


    private void initImgDatas() {
        imgText = new ArrayList<String>();
        imgUrls = new ArrayList<String>();
        int imgNum = Constants.IMAGES.length;
        for (int i=0;i < imgNum ; i++){
            imgText.add("img "+ i);
            imgUrls.add(Constants.IMAGES[i]);
        }
    }
}
