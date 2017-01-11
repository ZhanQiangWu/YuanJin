package net.yuanjin.widgetlib.photolib.album;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import net.yuanjin.widgetlib.R;
import net.yuanjin.widgetlib.imageloader.ImageLoaderUtils;
import net.yuanjin.widgetlib.imageloader.XtionImageLoader;
import net.yuanjin.widgetlib.imageviewpager.ImageUri;
import net.yuanjin.widgetlib.imageviewpager.ImageUriParams;
import net.yuanjin.widgetlib.imageviewpager.ImageViewPagerActivity;

import java.util.ArrayList;
import java.util.List;


/**
 *  Created by WuZhanQiang on 2016/9/20.
 */
public class AlbumChoiceAdapter1 extends BaseAdapter {

    private Context context;
    private AlbumIndexItem1 album;
    private boolean isMutipleChoice = false;//是否支持多选
    private GridView gridView;

//    private ImageLoader imageLoader;
//    private DisplayImageOptions options;
    private OnCheckBoxSelectedListener onCheckBoxSelectedListener;

    public AlbumChoiceAdapter1(Context context, AlbumIndexItem1 album, boolean isMutipleChoice, GridView gridView){
        this.context = context;
        this.album = album;
        this.isMutipleChoice = isMutipleChoice;
//        this.imageLoader = ImageLoader.getInstance();
        this.gridView = gridView;
        // TODO: 2017/1/10  Picasso 也需要做这个才行
        //this.gridView.setOnScrollListener(new PauseOnScrollListener(imageLoader,true,true));//在滚动和滑动中不显示图片

//        options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(false)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .build();


    }

    @Override
    public int getCount() {
        return album.getBitList().size();
    }

    @Override
    public AlbumPhotoItem1 getItem(int position) {
        return album.getBitList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView ==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_album_choice,null);
            holder = new ViewHolder();
            holder.init(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        AlbumPhotoItem1 photoItem = getItem(position);
        holder.setValue(convertView, photoItem, position);
        return convertView;
    }

    class ViewHolder{
        ImageView iv_photo;  //  照片
        CheckBox cb_selected; //  多选控件
        LinearLayout cb_layout;// 多选框加大点击区域

        public void init(View view) {
            this.iv_photo = (ImageView) view.findViewById(R.id.item_albumselected_photo);
            this.cb_selected = (CheckBox) view.findViewById(R.id.item_albumselected_cb);
            this.cb_layout = (LinearLayout) view.findViewById(R.id.item_albumselected_cblayout);
        }

        public void setValue(View view, final AlbumPhotoItem1 photoItem, final int position) {
            iv_photo.setImageResource(R.drawable.bg_gray_rd);
            // TODO: 2017/1/10   图片加载
            //imageLoader.displayImage(ImageDownloader.Scheme.FILE.wrap(photoItem.getPath()),iv_photo,options);
            XtionImageLoader.getInstance().load(ImageLoaderUtils.Scheme.FILE.wrap(photoItem.getPath())).into(iv_photo);
            //Picasso.with(context).load(ImageLoaderUtils.Scheme.FILE.wrap(photoItem.getPath())).into(iv_photo);
            //if (isMutipleChoice){//可多选
                cb_selected.setVisibility(View.VISIBLE);
                cb_layout.setVisibility(View.VISIBLE);
                cb_selected.setChecked(photoItem.isSelect());
                cb_selected.setClickable(false);//不可点击

                cb_layout.setOnClickListener(new View.OnClickListener() {//注册点击监听，覆盖checkbox

                    @Override
                    public void onClick(View v) {
                        boolean isCheck = !cb_selected.isChecked();//点击后的状态
                        if (onCheckBoxSelectedListener!=null){//额外的限制条件
                            if (onCheckBoxSelectedListener.validate(isCheck)){
                                onCheckBoxSelectedListener.onSelected(position,isCheck);
                                //通过条件
                                cb_selected.setChecked(isCheck);
                                photoItem.setSelect(isCheck);
                            }
                        }else {
                            //没有限制
                            cb_selected.setChecked(isCheck);
                            photoItem.setSelect(isCheck);
                        }
                    }

                });

                view.setOnClickListener(new View.OnClickListener() {//注册点击监听
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/9/20 待完 图片路径转换成ImageLoader要求的格式

                        //图片信息转换
                        List<ImageUri> uris = new ArrayList<ImageUri>();
                        for (AlbumPhotoItem1 item : album.getBitList()) {
                            //相册的图片路径都是本地路径
                            String localUrl = item.getPath();
                            ImageUri uri = new ImageUri(null,localUrl);
                            uris.add(uri);
                        }

                        Intent intent = new Intent();
                        intent.setClass(context,ImageViewPagerActivity.class);
                        intent.putExtra(ImageViewPagerActivity.DEFAULTPOSITION, position);//首显图片位置
                        intent.putExtra(ImageViewPagerActivity.IMAGEURIPARAMS,new ImageUriParams(uris));//本相册集所有图片信息
                        intent.putExtra(ImageViewPagerActivity.SAVEABLE,false);//不可保存
                        //intent.putExtra(ImageViewPagerActivity.FULLSCREEN,false);//
                        context.startActivity(intent);
                    }
                });
            //}else {//不可多选
            //    cb_selected.setVisibility(View.GONE);
            //    cb_layout.setVisibility(View.GONE);
            //}
        }

    }

    //外部控件监听checkBox状态事件
    public interface OnCheckBoxSelectedListener{
        public void onSelected(int position, boolean isCheck);
        public boolean validate(boolean isCheck);
    }

    public void setOnCheckBoxSelectedListener(OnCheckBoxSelectedListener listener){
        this.onCheckBoxSelectedListener = listener;
    }
}
