package net.yuanjin.widgetlib.imageviewpager;

import android.text.TextUtils;

import java.io.Serializable;

/**
 *  Created by WuZhanQiang on 2016/9/21.
 */
public class ImageUri implements Serializable {

    private static final long serialVersionUID = 1L;

    private String localPath;//本地图片路径 eg:/storage/emulated/0/DCIM/Camera/IMG20160913223141.jpg
    private String uploadUrl;//服务端路径，文件id

    /**
     * ImageUri 初始化
     * @param uploadUrl 服务端路径
     * @param localPath 本地路径
     */
    public ImageUri(String uploadUrl, String localPath  ) {
        this.uploadUrl = uploadUrl;
        this.localPath =localPath;
    }

    public void setUploadUrl(String uploadUrl){
        this.uploadUrl = uploadUrl;
    }

    public void setLocalImageLoaderPath(String localPath){
        this.localPath = localPath;
    }


    public String getUploadUrl() {
        return uploadUrl;
    }

    /**
     * 返回图片本地路径
     * @return  eg：/storage/emulated/0/DCIM/Camera/IMG20.jpg ，不含 "file://" 头
     */
    public String getLocalPath(){
        return localPath;
    }

    public boolean isEmpty(){
        return TextUtils.isEmpty(localPath) && TextUtils.isEmpty(uploadUrl);
    }



    /**
     * 存在本地路径
     * @return boolean
     */
    public boolean hasLocalPath(){
        return !TextUtils.isEmpty(localPath);
    }
}

