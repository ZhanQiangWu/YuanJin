package xtion

class ApiContainer {
    //阿里云演示环境
    final static Api Ali = new Api(name: 'Ali',
            url: 'http://ali.com',
            appicon: '@drawable/icon_office',
            appId:'net.xtion.crm.uk100')
    //阿里云开发环境
    final static Api Baidu = new Api(name: 'Baidu',
            url: 'http://baidu.com',
            appicon: '@drawable/icon_office',
            appId:'net.xtion.crm.uk100')
    //公司服务器内网开发环境
    final static Api Tencent = new Api(name: 'Tencent',
            url: 'http://tencent.com',
            appicon: '@drawable/icon_dev',
            appId:'net.xtion.crm.uk100')

    final static def apiFlavors = [Ali,Baidu, Tencent]
}

class Api {
    String name
    String appicon;
    String appId;
    String url;
}