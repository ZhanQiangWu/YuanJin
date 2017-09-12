package net.yuanjin.mytest.mvp.simples.simple_2;

import net.yuanjin.mytest.mvp.simples.util.HttpUtil;

/**
 * P层
 * 特点：持有UI层和数据层引用
 * Created by wzq on 2027/9/7.
 */
public class LoginPresenter_2 {

    //持有M层引用
    private LoginModel_2 model;

    //持有UI层引用
    private LoginView_2 loginView;

    public LoginPresenter_2(){
        this.model = new LoginModel_2();
    }

    public void attachView(LoginView_2 loginView){
        this.loginView = loginView;
    }

    public void detachView(){
        this.loginView = null;
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
