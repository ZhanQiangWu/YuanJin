package net.yuanjin.mvp.simples.simple_1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import net.yuanjin.R;
import net.yuanjin.mvp.simples.simple_1.utils.HttpTask;
import net.yuanjin.mvp.simples.simple_1.utils.HttpUtil;

/**
 *  Created by wzq on 2017/9/5.
 */

public class MVPActivity1 extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp1);
    }

    //MVP设计 - 第一步 - 普通代码
    public void login(View view){
        HttpTask httpTask = new HttpTask(new HttpUtil.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                Toast.makeText(MVPActivity1.this,result,Toast.LENGTH_LONG).show();
            }
        });
        httpTask.execute("Dream","123456");
    }

}
