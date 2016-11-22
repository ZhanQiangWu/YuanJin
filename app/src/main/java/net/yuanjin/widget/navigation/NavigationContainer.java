package net.yuanjin.widget.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import net.yuanjin.R;

/**
 *  标题导航栏
 *  Created by zhan on 2016/11/22.
 */

public abstract class NavigationContainer extends LinearLayout{

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

    }
}
