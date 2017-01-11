package net.yuanjin.widgetlib.imageviewpager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import net.yuanjin.widgetlib.R;
import net.yuanjin.widgetlib.imageloader.ImageLoaderUtils;
import net.yuanjin.widgetlib.imageloader.ImageLoadingCallBack;
import net.yuanjin.widgetlib.imageloader.XtionImageLoader;

import java.util.ArrayList;
import java.util.List;


import uk.co.senab.photoview.PhotoView;

import static android.widget.ImageView.ScaleType;

//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.assist.FailReason;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.squareup.picasso.Picasso;

/**
 *  Created by WuZhanQiang on 2016/9/21.
 */

public class ImageViewPager extends ViewPager {

    private boolean fullscreen;//是否显示完整的图片
//    private ImageLoadingListener loadingListener;
//    private ImageLoader imageLoader;
    private ImageLoadingCallBack loadingCallBack;
    private List<ImageUri> uris=new ArrayList<ImageUri>();
    private MyPagerAdapter adapter;
    private ScaleType scaleType = ScaleType.CENTER;

    public ImageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new MyPagerAdapter(context,uris);
//        imageLoader = ImageLoader.getInstance();
        setAdapter(adapter);
        setOffscreenPageLimit(1);
    }

    /**
     * 更新ViewPager数据
     * @param urlsArr :图片服务端路径 数组集合
     */
    public void setData(String[] urlsArr){
        this.uris.clear();
        for (String url:urlsArr){
            this.uris.add(new ImageUri(url,null));
        }
        this.adapter.notifyDataSetChanged();
    }

    /**
     * 更新ViewPager数据
     * @param imageUris ：ImageUri 集合
     */
    public void setData(List<ImageUri> imageUris){
        this.uris.clear();
        this.uris.addAll(imageUris);
        this.adapter.notifyDataSetChanged();
    }

    /**
     * 是否显示完整的图片
     * @param fullscreen ：boolean
     */
    public void setFullscreen(boolean fullscreen){
        this.fullscreen = fullscreen;
    }

//    /**
//     * 注册图片下载监听
//     * @param loadingListener ：ImageLoadingListener
//     */
//    public void setImageLoadingListener(ImageLoadingListener loadingListener){
//        this.loadingListener = loadingListener;
//    }

    /**
     * 注册图片下载回调监听
     * @param loadingCallBack loadingCallBack
     */
    public void setImageLoadingCallBack(ImageLoadingCallBack loadingCallBack){
        this.loadingCallBack = loadingCallBack;
    }

    /**
     * 设置图片显示类型
     * @param scaleType ：ScaleType
     */
    public void setScaleType(ScaleType scaleType){
        this.scaleType = scaleType;
    }


    private class MyPagerAdapter extends PagerAdapter {

        private Context context;
        private List<ImageUri> uris;

        public MyPagerAdapter(Context context, List<ImageUri> uris) {
            this.context = context;
            this.uris = uris;
        }

        @Override
        public int getCount() {
            return uris.size();
        }

        @Override
        public Object instantiateItem(View container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_prewview, null);
            final PhotoView imageview = (PhotoView) view.findViewById(R.id.item_prewview_image);
            final LinearLayout layout_progress = (LinearLayout) view.findViewById(R.id.item_prewview_layoutprogress);
            final TextView tv_progress = (TextView) view.findViewById(R.id.item_prewview_tvprogress);

            //TODO 小图片全屏问题
//            if (fullscreen && uris.get(position).localPath.startsWith("headimg")) {
//                imageview.setScaleType(ScaleType.FIT_CENTER);
//            }
            imageview.setScaleType(scaleType);
            final Activity activity = (Activity) getContext();
            tv_progress.setVisibility(View.GONE);

            // TODO: 2017/1/10 以下的图片加载监听过程需要查看是否需要重写

//            //图片下载过程监听
//            ImageLoadingListener listener = new ImageLoadingListener() {
//
//                @Override
//                public void onLoadingStarted(final String imageUri, final View view) {
//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            layout_progress.setVisibility(View.VISIBLE);
//                            if (loadingListener!=null){
//                                loadingListener.onLoadingStarted(imageUri,view);
//                            }
//                        }
//                    });
//                }
//
//                @Override
//                public void onLoadingFailed(final String imageUri, final View view, final FailReason failReason) {
//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            layout_progress.setVisibility(View.GONE);
//                            imageview.setImageResource(R.mipmap.img_photoprew_failed);
//                            if(loadingListener!=null)loadingListener.onLoadingFailed(imageUri,view,failReason);
//                        }
//                    });
//                }
//
//                @Override
//                public void onLoadingComplete(final String imageUri, final View view, final Bitmap loadedImage) {
//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            layout_progress.setVisibility(View.GONE);
//                            if (loadingListener!=null) loadingListener.onLoadingComplete(imageUri,view,loadedImage);
//                        }
//                    });
//                }
//
//                @Override
//                public void onLoadingCancelled(final String imageUri, final View view) {
//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            layout_progress.setVisibility(View.GONE);
//                            if (loadingListener!=null) loadingListener.onLoadingCancelled(imageUri,view);
//                        }
//                    });
//                }
//            };

            ImageUri imageuri = uris.get(position);
            if(imageuri.hasLocalPath()){
                // TODO: 2017/1/10
                //优先读本地路径
                //imageLoader.displayImage("file://"+imageuri.getLocalPath(),imageview,listener);
                XtionImageLoader.getInstance().load(ImageLoaderUtils.Scheme.FILE.wrap(imageuri.getLocalPath())).into(imageview);
            }else{
                //读url
                //imageLoader.displayImage(imageuri.getUploadUrl(),imageview,listener);
                XtionImageLoader.getInstance().load(imageuri.getUploadUrl()).into(imageview);
            }

//            String url = imageuri.getUploadUrl;
//            if (!TextUtils.isEmpty(url)){//服务端路径 url 非空
//                if (url.startsWith("headimage")){
//                    //// TODO: 2016/9/22  有bug ，需要修改
//                    //默认头像
//                    url = ImageDownloader.Scheme.HEADIMG.crop(url);
//                    url = FileUtil.Scheme.HEADIMG.wrap(url);
//                }else {
//                    url = ImageDownloader.Scheme.DETAIL.wrap(url);
//                    url = FileUtil.Scheme.DETAIL.wrap(url);
//                }
//            }else {//服务端路径 url为空
//                //本地路径
//                url = imageuri.localPath;
//                url = ImageDownloader.Scheme.FILE.wrap(url);
//            }



            //将实例化的视图添加到viewpager容器中
            ((ViewPager)container).addView(view);
            return view;
        }

        // TODO: 2016/9/21  未完成
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView((View) object);
        }
    }


    // TODO: 2016/9/21 看下后面这里可不可以去掉
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
