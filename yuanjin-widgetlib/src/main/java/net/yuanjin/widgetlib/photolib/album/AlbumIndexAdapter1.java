package net.yuanjin.widgetlib.photolib.album;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.yuanjin.widgetlib.R;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Created by WuZhanQiang on 2016/9/19.
 */
public class AlbumIndexAdapter1 extends BaseAdapter {

    private List<AlbumIndexItem1> albumList;
    private Context context;
    private ViewHolder holder;
    @SuppressLint("UseSparseArrays")
    private Map<Integer,ViewHolder> cacheViews=new HashMap<Integer, ViewHolder>();
    //ImageLoader imageLoader;

    @Deprecated
    private boolean isBusy = false;//gridview滑动判断,ImageLoader已实现，现弃用

    public AlbumIndexAdapter1(List<AlbumIndexItem1> list , Context context){
        this.albumList = list;
        this.context=context;
        //imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public AlbumIndexItem1 getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_album_index,null);
            holder = new ViewHolder();
            holder.iv = (ImageView)convertView.findViewById(R.id.item_albumindex_image);
            holder.tv = (TextView)convertView.findViewById(R.id.item_albumindex_name);
            holder.cb = (CheckBox)convertView.findViewById(R.id.item_albumindex_cb);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        /** 通过ID 获取缩略图*/
        holder.iv.setImageResource(R.drawable.bg_gray_rd);
        AlbumIndexItem1 item = getItem(position);
        //if (!isBusy()){
        //imageLoader.displayImage(ImageDownloader.Scheme.FILE.wrap(item.getBitmap()),holder.iv);
        //XtionImageLoader.getInstance().load(ImageLoaderUtils.Scheme.FILE.wrap(item.getBitmap())).into(holder.iv);
        Picasso.with(context)
                .load(new File(item.getBitmap()))
                .config(Bitmap.Config.RGB_565)
                .resize(200,200)
                .into(holder.iv);
        //}
        holder.tv.setText(item.getName()+" ( "+item.getCount()+" ) ");
        holder.cb.setChecked(true);
        holder.cb.setClickable(false);
        if (item.isSelected()){
            holder.cb.setVisibility(View.VISIBLE);
        }else {
            holder.cb.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView iv;
        TextView tv;
        CheckBox cb;
    }

    @Deprecated
    public synchronized void  setBusy(boolean isBusy){
        this.isBusy=isBusy;
    }

    @Deprecated
    public synchronized boolean isBusy(){
        return isBusy;
    }
}
