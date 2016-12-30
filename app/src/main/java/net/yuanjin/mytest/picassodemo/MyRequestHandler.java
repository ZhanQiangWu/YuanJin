package net.yuanjin.mytest.picassodemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import net.yuanjin.R;

import java.io.IOException;

/**
 *  Created by WuZhanQiang on 2016/12/30.
 */

public class MyRequestHandler extends RequestHandler{

    private Context context;

    MyRequestHandler(Context context){
        this.context = context;
    }

    private final String tag = "mytest";
    private static final String EAT_FOODY_RECIPE_SCHEME = "headimg";

    @Override
    public boolean canHandleRequest(Request data) {

        //判断当前请求是否是自定义的URI (eg:eatfoody://cupcake)  ,如果是就会调用load方法
        Log.i(tag,data.uri.getScheme()+" : "+data.uri);

        return EAT_FOODY_RECIPE_SCHEME.equals(data.uri.getScheme());

    }

    @Override
    public Result load(Request request, int networkPolicy) throws IOException {
        // 我们可以进行网络请求或者进行本地加载,然后返回一个Result

        // 从请求中获取一个key值
        // 如果自定义URI为: "eatfoody://cupcake", key的值为 "cupcake"
        String imageKey = request.uri.getHost();

        Bitmap bitmap;
        //加载本地图片
        if (imageKey.contentEquals("f01")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.f01);
        }else if (imageKey.contentEquals("f02")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.f02);
        }else if (imageKey.contentEquals("f03")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.f03);
        }else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.f04);
        }

        return new Result(bitmap, Picasso.LoadedFrom.DISK);
//        return null;
    }
}
