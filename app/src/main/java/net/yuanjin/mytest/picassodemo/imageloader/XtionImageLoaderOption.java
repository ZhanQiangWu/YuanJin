package net.yuanjin.mytest.picassodemo.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import net.yuanjin.R;

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


    public XtionImageLoaderOption(XtionImageLoader xtionImageLoader, String path) {

        this.xtionImageLoader = xtionImageLoader;
        this.path = path;

        if (type == ImageLoaderType.Picasso)
        this.requestCreator = Picasso.with(xtionImageLoader.context).load(ImageLoaderUtils.getUri(path));

        //initData();
    }

    /**
     * 初始化默认配置
     */
    private void initData() {
        this.bitmapConfig = Bitmap.Config.RGB_565;
        this.type = ImageLoaderType.Picasso;
        this.errorResId = R.mipmap.ic_error;
        this.placeholderResId = R.mipmap.ic_empty;
    }

    /**
     * 设置加载前的图片显示
     * @param placeholderResId 图片id
     * @return
     */
    public XtionImageLoaderOption placeHolder(int placeholderResId){
        this.placeholderResId = placeholderResId;

        if (type == ImageLoaderType.Picasso && this.requestCreator !=null)
        this.requestCreator.placeholder(placeholderResId);
        return this;
    }

    /**
     * 设置图片加载错误时的图片显示
     * @param errorResId
     * @return
     */
    public XtionImageLoaderOption error(int errorResId){
        this.errorResId = errorResId;

        if (type == ImageLoaderType.Picasso && this.requestCreator !=null)
            this.requestCreator.error(errorResId);
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

        if (type == ImageLoaderType.Picasso && this.requestCreator !=null)
            this.requestCreator.resize(targetWidth,targetHeight);
        return this;
    }

    /**
     * 设置Bitmap 配置
     * @param config
     * @return
     */
    public XtionImageLoaderOption bitmapConfig(Bitmap.Config config){
        this.bitmapConfig = config;

        if (type == ImageLoaderType.Picasso && this.requestCreator !=null)
            this.requestCreator.config(config);
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
                .into(target);

//        this.requestCreator.into(target);
    }



}
