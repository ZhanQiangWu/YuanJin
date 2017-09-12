package net.yuanjin.mytest.mvp.simples.simple_1;

import net.yuanjin.mytest.mvp.simples.util.HttpUtil;

/**
 * P层
 * 特点：持有UI层和数据层引用
 * Created by wzq on 2017/9/7.
 */
public class LoginPresenter_1 {

    //持有M层引用
    private LoginModel_1 model;

    //持有UI层引用
    private LoginView_1 loginView;

    public LoginPresenter_1(LoginView_1 loginView){
        this.model = new LoginModel_1();
        this.loginView = loginView;
    }

    public void login(String username, String password){
        model.login(username, password, new HttpUtil.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                //回调UI层->和UI进行交互
                if (loginView != null){
                    loginView.onLoginResult(result);
                }
            }
        });

    }
}
