package net.yuanjin.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import net.yuanjin.R;
import net.yuanjin.widget.navigation.NavigationText;

/**
 *  Created by WuZhanQiang on 2016/11/15.
 */

public class BasicActivity extends AppCompatActivity{

    private NavigationText navigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //标题栏设置
        if (Build.VERSION.SDK_INT < 11){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        //主题设置
        setTheme(R.style.YuanJinTheme);

        setNavigation(getDefaultNavigation());
    }


    /**
     * 功能描述：动态设置actionbar
     * @param navigation
     */
    public void setNavigation(View navigation){
        ActionBar actionBar = getSupportActionBar();
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                        ViewGroup.LayoutParams.MATCH_PARENT);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setCustomView(navigation,layoutParams);
        if (navigation instanceof NavigationText){
            this.navigation = (NavigationText) navigation;
        }
        actionBar.show();
    }

    private NavigationText getDefaultNavigation(){
        if (navigation==null){
            navigation = new NavigationText(this);
        }
        return navigation;
    }
}
