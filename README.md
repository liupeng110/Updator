# Updator简介
为你的安卓app加入更新模块吧！Updator只需要简单的配置就能够完成下载更新包，安装更新包的功能~
# 导入工程

# 使用方法
1.在使用之前初始化，比如在Application中：
```Java
@Override
public void onCreate() {
  super.onCreate();
  Updator.init("1.1.2.0", //当前版本号
  "https://raw.githubusercontent.com/ukiy2010/testJson/master/README.md");//接口地址
}
```
2.在要调用的地方启动之
```Java
Updator.start(mContext);
```
# json格式要求
所调用的接口返回json格式如下
```JavaScript
{
  "success": true,
  "info": "success",
  "data": {
    "new_version": "1.2.1.0",
    "min_version": "1.1.1.0",
    "change_log": "1、修复若干bug 2、流畅性增加 3、多重新能提升",
    "url": "http://dd.myapp.com/16891/445EDA31D2839EA4DA3334BF214BB084.apk?fsname=com.tencent.tmgp.mhxy.sqsy_1.66.0_10660.apk"
  }
}
```
