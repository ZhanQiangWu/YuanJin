package net.yuanjin.mvp.login.model;

/**
 *  Created by zhan on 2016/12/9.
 */

public interface IUser {

    String getName();

    String getPasswd();

    int checkUserValidity(String name, String passwd);

}
