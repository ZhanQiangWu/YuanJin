package xtion

class ApiContainer {
    //阿里云演示环境
    final static Api AliUkeRELEASE = new Api(name: 'ALiUkeReleaseApi',
            apiHost: 'http://baidu.com',
            fileHost: 'http://uke.xtioncrm.com:80',
            pushHost: 'uke.xtioncrm.com',
            pushPort: '80',
            appicon: '@drawable/icon_office',
            appId:'net.xtion.crm.uk100')
    //阿里云开发环境
    final static Api AliCRMRELEASE = new Api(name: 'ALiCrmReleaseApi',
            apiHost: 'http://crm.xtioncrm.com:80',
            fileHost: 'http://crm.xtioncrm.com:80',
            pushHost: 'crm.xtioncrm.com',
            pushPort: '80',
            appicon: '@drawable/icon_office',
            appId:'net.xtion.crm.uk100')
    //公司服务器内网开发环境
    final static Api InnerDev = new Api(name: 'InnerDevApi',
            apiHost: 'http://172.16.0.121:80',
            fileHost: 'http://172.16.0.121:80',
            pushHost: '172.16.0.121',
            pushPort: '80',
            appicon: '@drawable/icon_dev',
            appId:'net.xtion.crm.uk100')
    //公司服务器外网开发环境
    final static Api OuterDev = new Api(name: 'OuterDevApi',
            apiHost: 'http://183.63.72.242:81',
            fileHost: 'http://183.63.72.242:81',
            pushHost: '183.63.72.242',
            pushPort: '81',
            appicon: '@drawable/icon_dev',
            appId:'net.xtion.crm.uk100')
    //公司服务器内网测试环境
    final static Api InnerTEST = new Api(name: 'InnerTestApi',
            apiHost: 'http://172.16.0.121:81',
            fileHost: 'http://172.16.0.121:81',
            pushHost: '172.16.0.121',
            pushPort: '81',
            appicon: '@drawable/icon_test',
            appId:'net.xtion.crm.uk100')
    //公司服务器外网测试环境
    final static Api OuterTEST = new Api(name: 'OuterTestApi',
            apiHost: 'http://183.63.72.242:12601',
            fileHost: 'http://183.63.72.242:12601',
            pushHost: '183.63.72.242',
            pushPort: '12601',
            appicon: '@drawable/icon_test',
            appId:'net.xtion.crm.uk100')
    //公司服务器内网正式环境
    final static Api InnerRELEASE = new Api(name: 'InnerReleaseApi',
            apiHost: 'http://172.16.0.121:82',
            fileHost: 'http://172.16.0.121:82',
            pushHost: '172.16.0.121',
            pushPort: '82',
            appicon: '@drawable/icon_office',
            appId:'net.xtion.crm.uk100')
    //公司服务器外网正式环境
    final static Api OuterRELEASE = new Api(name: 'OuterReleaseApi',
            apiHost: 'http://183.63.72.242:12602',
            fileHost: 'http://183.63.72.242:12602',
            pushHost: '183.63.72.242',
            pushPort: '12602',
            appicon: '@drawable/icon_office',
            appId:'net.xtion.crm.uk100')

    final static def apiFlavors = [InnerTEST,OuterTEST, AliUkeRELEASE,AliCRMRELEASE, InnerDev, OuterDev,InnerRELEASE,OuterRELEASE]
}

class Api {
    String name
    String apiHost;
    String fileHost;
    String pushHost;
    String pushPort;
    String appicon;
    String appId;
}