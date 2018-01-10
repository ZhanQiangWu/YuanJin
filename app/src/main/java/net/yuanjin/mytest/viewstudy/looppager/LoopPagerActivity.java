package net.yuanjin.mytest.viewstudy.looppager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

import java.util.ArrayList;

public class LoopPagerActivity extends BasicActivity {

    private ViewPager vp;
    private ArrayList<String> urls = new ArrayList<>();
    private LoopVPAdapter loopVPAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looppager);
        vp = (ViewPager) findViewById(R.id.vp);

        urls.add("http://seopic.699pic.com/photo/00005/5186.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50010/0719.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50009/9449.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50002/5923.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50001/9330.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50009/9191.jpg_wh1200.jpg");

        loopVPAdapter = new ImgAdapter(this,urls,vp);

    }


}
