package net.yuanjin.widget.navigation;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import net.yuanjin.R;

/**
 *  标题导航栏
 *  Created by zhan on 2016/11/22.
 */

public abstract class NavigationContainer extends LinearLayout{

    private RelativeLayout root;
    private LinearLayout layout_center,layout_right,layout_right_custom;
    private NavigationButton btn_left,btn_right,btn_right2;

    public NavigationContainer(Context context) {
        super(context);
        init(context);
    }

    public NavigationContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.navigation_container,this);

        root = (RelativeLayout) findViewById(R.id.navigation_root);
        layout_center = (LinearLayout) findViewById(R.id.navigation_layout_center);
        layout_right = (LinearLayout) findViewById(R.id.navigation_layout_right);
        layout_right_custom = (LinearLayout) findViewById(R.id.navigation_layout_customright);

        btn_left = (NavigationButton) findViewById(R.id.navigation_btn_left);
        btn_right = (NavigationButton) findViewById(R.id.navigation_btn_right);
        btn_right2 = (NavigationButton) findViewById(R.id.navigation_btn_right2);

        btn_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity){
                    ((Activity)getContext()).finish();
                }
            }
        });
        btn_right.hide();
        View centerView = setCenterView();
        if (centerView!=null){
            layout_center.removeAllViews();
            layout_center.addView(centerView);
        }

    }

    public abstract View setCenterView();

    public NavigationButton getLeftButton(){
        return btn_left;
    }
}
