package net.yuanjin.mvp.simples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import net.yuanjin.R;
import net.yuanjin.mvp.simples.simple_1.LoginPresenter_1;
import net.yuanjin.mvp.simples.simple_1.LoginView_1;
import net.yuanjin.mvp.simples.simple_2.LoginPresenter_2;
import net.yuanjin.mvp.simples.simple_2.LoginView_2;
import net.yuanjin.mvp.simples.util.HttpTask;
import net.yuanjin.mvp.simples.util.HttpUtil;

/**
 *  Created by wzq on 2017/9/5.
 */

public class MVPActivity1 extends AppCompatActivity implements LoginView_2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp1);
    }


    //MVP设计 - 第一步 - 普通代码
//    public void login(View view){
//        HttpTask httpTask = new HttpTask(new HttpUtil.OnHttpResultListener() {
//            @Override
//            public void onResult(String result) {
//                Toast.makeText(MVPActivity1.this,result,Toast.LENGTH_LONG).show();
//            }
//        });
//        httpTask.execute("Dream","123456");
//    }

    /**
     * 2、MVP设计->第二步->优化代码-优化第1步
     * 分析问题：团队开发（存在问题：多人开发、模块化、迭代）
     * 解决方案：MVP设计（UI层和M层进行分离）
     * 解决P层和V层直接关联->接口回调
     */
//    public void login(View view) {
//        LoginPresenter_1 presenter1 = new LoginPresenter_1(this);
//        presenter1.login("Dream","123456");
//    }
    private LoginPresenter_2 presenter;

    public void login(View view) {
        presenter = new LoginPresenter_2();
        presenter.attachView(this);
        presenter.login("Dream","123456");
    }

    @Override
    public void onLoginResult(String result) {
        Toast.makeText(MVPActivity1.this,result,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
