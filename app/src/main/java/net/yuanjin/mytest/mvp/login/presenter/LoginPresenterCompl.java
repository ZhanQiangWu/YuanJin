package net.yuanjin.mytest.mvp.login.presenter;

import android.os.Handler;
import android.os.Looper;

import net.yuanjin.mytest.mvp.login.model.IUser;
import net.yuanjin.mytest.mvp.login.model.UserModel;
import net.yuanjin.mytest.mvp.login.view.ILoginView;


/**
 *  Created by WuZhanQiang on 2016/12/9.
 */

public class LoginPresenterCompl implements ILoginPresenter{

    ILoginView iLoginView;
    IUser user;
    Handler handler;

    public LoginPresenterCompl(ILoginView iLoginView){
        this.iLoginView = iLoginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    private void initUser() {
        user = new UserModel("mvp","mvp");
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String name, String passwd) {
        final boolean isLoginSuccess = user.checkUserValidity(name,passwd);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(isLoginSuccess);
            }
        },5000);
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iLoginView.onSetProgressBarVisibility(visiblity);
    }
}
