package net.yuanjin.widget.navigation;

import android.content.Context;
import android.opengl.Visibility;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.yuanjin.R;

/**
 *  Created by zhan on 2016/11/23.
 */

public class NavigationButton extends LinearLayout{

    private ImageView imageView;
    private TextView textView;
    private LinearLayout root;

    public NavigationButton(Context context) {
        super(context);
        init();
    }

    public NavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_navigation_button,this);
        root = (LinearLayout) findViewById(R.id.navigation_btn_root);
        imageView = (ImageView) findViewById(R.id.navigation_btn_img);
        textView = (TextView) findViewById(R.id.navigation_btn_tv);
    }

    protected void setRootGravity(int gravity){
        root.setGravity(gravity);
    }

    protected void setImageResource(int resource){
        this.imageView.setImageResource(resource);
    }

    protected void hide(){
        hide(false);
    }

    protected void hide(boolean isGone){
        if (isGone){
            this.setVisibility(View.GONE);
        }else{
            this.setVisibility(View.INVISIBLE);
        }
        this.setClickable(false);
    }

}
