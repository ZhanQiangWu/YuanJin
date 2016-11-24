package net.yuanjin.widget.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.yuanjin.R;

/**
 *  Created by zhan on 2016/11/24.
 *  默认文字标题ActionBar
 */

public class NavigationText extends NavigationContainer{

    private TextView tv_title;

    public NavigationText(Context context) {
        super(context);
    }

    public NavigationText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View setCenterView() {
        View view =((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.navigation_text,null);
        tv_title = (TextView) view.findViewById(R.id.navigation_text_title);
        return view;
    }

    public NavigationText setTitle(String text) {
        tv_title.setText(text);
        return this;
    }
}
