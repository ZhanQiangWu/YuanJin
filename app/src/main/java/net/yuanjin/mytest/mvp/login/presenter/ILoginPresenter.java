package net.yuanjin.mytest.mvp.login.presenter;

/**
 *  Created by zhan on 2016/12/9.
 */

public interface ILoginPresenter {

    void clear();
    void doLogin(String name, String passwd);
    void setProgressBarVisiblity(int visiblity);

}
