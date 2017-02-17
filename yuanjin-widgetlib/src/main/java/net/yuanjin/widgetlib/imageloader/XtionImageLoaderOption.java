package net.yuanjin.widgetlib.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import net.yuanjin.widgetlib.R;

/**
 *  Created by WuZhanQiang on 2017/1/6.
 */

public class XtionImageLoaderOption {

    private final XtionImageLoader xtionImageLoader;
    private RequestCreator requestCreator;// Picasso

    private int placeholderResId;
    private int errorResId;
    private int targetWidth,targetHeight;
    private Bitmap.Config bitmapConfig ;
    private ImageLoaderType type = ImageLoaderType.Picasso;
    private String path;
    private ImageLoadingCallBack callBack;


    public XtionImageLoaderOption(XtionImageLoader xtionImageLoader, String path) {

        this.xtionImageLoader = xtionImageLoader;
        this.path = ImageLoaderUtils.getUri(path);

        initData();

//        if (type == ImageLoaderType.Picasso){
//            this.requestCreator = Picasso.with(xtionImageLoader.context)
//                    .load(ImageLoaderUtils.getUri(path));
//            requestCreatorInit(this.requestCreator);
//        }

    }

    public XtionImageLoaderOption(XtionImageLoader xtionImageLoader){
        this.xtionImageLoader = xtionImageLoader;
    }

    /**
     * RequestCreator初始化默认配置
     */
    private void initData( ) {
        this.bitmapConfig = Bitmap.Config.RGB_565;
        this.type = ImageLoaderType.Picasso;
        this.errorResId = R.mipmap.img_error;
        this.placeholderResId = R.mipmap.img_empty;
        this.targetWidth = 200;
        this.targetHeight = 200;

//        request.config(Bitmap.Config.RGB_565);
//        request.error(R.mipmap.img_error);
//        request.placeholder(R.mipmap.img_empty);
    }

    /**
     * 设置加载前的图片显示
     * @param placeholderResId 图片id
     * @return
     */
    public XtionImageLoaderOption placeHolder(int placeholderResId){
        this.placeholderResId = placeholderResId;

//        if (type == ImageLoaderType.Picasso && this.requestCreator !=null)
//        this.requestCreator.placeholder(placeholderResId);
        return this;
    }

    /**
     * 设置图片加载错误时的图片显示
     * @param errorResId
     * @return
     */
    public XtionImageLoaderOption error(int errorResId){
        this.errorResId = errorResId;

//        if (type == ImageLoaderType.Picasso && this.requestCreator !=null)
//            this.requestCreator.error(errorResId);
        return this;
    }

    /**
     * 设置图片为指定大小
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public XtionImageLoaderOption resize(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;

//        if (type == ImageLoaderType.Picasso && this.requestCreator !=null)
//            this.requestCreator.resize(targetWidth,targetHeight);
        return this;
    }

    /**
     * 设置Bitmap 配置
     * @param config
     * @return
     */
    public XtionImageLoaderOption bitmapConfig(Bitmap.Config config){
        this.bitmapConfig = config;

//        if (type == ImageLoaderType.Picasso && this.requestCreator !=null)
//            this.requestCreator.config(config);
        return this;
    }

    /**
     * 设置图片的框架类型
     * @param type Picasso（默认） / ImageLoader
     * @return
     */
    public XtionImageLoaderOption imageLoaderType(ImageLoaderType type){
        this.type = type;
        return this;
    }

    /**
     * 指定图片显示的 imageview
     * @param target
     */
    public void into(ImageView target){
        into(target,null);
    }

    /**
     * 指定图片显示的 imageview 和 callback
     * @param target
     * @param callBack
     */
    public void into(ImageView target, ImageLoadingCallBack callBack){
        this.callBack = callBack;

        //根据图片加载组件的类型进行加载区分
        switch (type){

            case Picasso:
                doPicasso(target);
                break;

            case ImageLoader:
                this.requestCreator = null;
                break;
        }
    }


    enum ImageLoaderType{
        Picasso,ImageLoader
    }


    private void doPicasso(ImageView target){
        // TODO: 2017/1/6 还要去弄那个图片字段类型的适配
        Picasso.with(this.xtionImageLoader.context)
                .load(this.path)
                .placeholder(this.placeholderResId)
                .error(this.errorResId)
                .resize(this.targetWidth,this.targetHeight)
                .config(this.bitmapConfig)
                .into(target, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (callBack != null){
                            callBack.onSuccess();
                        }
                    }

                    @Override
                    public void onError() {
                        if (callBack != null){
                            callBack.onError();
                        }
                    }
                });

//        this.requestCreator.into(target, new Callback() {
//
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
    }

//    /**
//     * 通过服务端路径获取本地缓存
//     * @param path 图片的服务端路径
//     * @return
//     */
//    public Bitmap getDiskCache(String path){
//        switch (type){
//            case Picasso:
//                try {
//                    // TODO: 2017/1/11 可以的话这里还是要完善才好，因为又重复做了取bitmap的操作
//                    return Picasso.with(xtionImageLoader.context).load(path)
//                            .networkPolicy(NetworkPolicy.OFFLINE).get();//OFFLINE只从本地取
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//
//            case ImageLoader:
//
//
//            default:
//                return null;
//        }
//    }



}
