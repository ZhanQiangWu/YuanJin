package net.yuanjin.widgetlib.imageloader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import net.yuanjin.widgetlib.R;
import net.yuanjin.widgetlib.XtionBasicActivity;


/**
 *  Created by WuZhanQiang on 2017/1/6.
 */

public class XtionImageLoaderDemo extends XtionBasicActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_imageloaderdemo);

        ImageView imageView = (ImageView) findViewById(R.id.iv_imageloader1);

        XtionImageLoader.getInstance()
                .load("headimg://F01.png")
                //.load("http://d.hiphotos.baidu.com/zhidao/pic/item/adaf2edda3cc7cd9f6effbcf3d01213fb90e91e2.jpg")
                //.load("https://raw.githubusercontent.com/square/picasso/master/website/static/sample.png")
                //.load("https://42.159.86.120:82/bs/b9ae8a02-117f-40b7-b55e-ee1090a9e362.png?ts=1482892773&sc=200&ex=300&si=9c04b318f5ccc2b68fd8869c0df74f66")
                .placeHolder(R.mipmap.img_empty)
                .error(R.mipmap.img_error)
                .resize(400,400)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .into(imageView);
//        XtionImageLoader.with(getApplicationContext())
//                .load("http://d.hiphotos.baidu.com/zhidao/pic/item/adaf2edda3cc7cd9f6effbcf3d01213fb90e91e2.jpg")
//                .placeHolder(R.mipmap.img_empty)
//                .error(R.mipmap.img_error)
//                .resize(400,400)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .into(imageView);
    }
}
