package net.yuanjin.mytest.viewstudy.looppager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *  Created by Administrator on 2016/9/13.
 */
public class ImgAdapter extends LoopVPAdapter<String> {

    public ImgAdapter(Context context, ArrayList<String> datas, ViewPager viewPager) {
        super(context, datas, viewPager);
    }

    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected View getItemView(String data) {
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

//        ImageView imageView = new ImageView(mContext);
//        imageView.setLayoutParams(layoutParams);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setBackgroundColor();
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(layoutParams);
        textView.setText(data);

        return textView;
    }


}
