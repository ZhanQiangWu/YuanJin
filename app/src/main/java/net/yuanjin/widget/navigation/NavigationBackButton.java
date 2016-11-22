package net.yuanjin.widget.navigation;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import net.yuanjin.R;

/**
 *  Created by zhan on 2016/11/23.
 */

public class NavigationBackButton extends NavigationButton{

    public NavigationBackButton(Context context) {
        super(context);
        init();
    }

    public NavigationBackButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setRootGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        setImageResource(R.mipmap.actionbar_back);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)v.getContext()).finish();
            }
        });
    }
}
