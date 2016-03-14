# Updator简介
为你的安卓app加入更新模块吧！Updator只需要简单的配置就能够完成下载更新包，安装更新包的功能~
# 导入工程
在你app的build.gradle里面添加如下代码：
```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.ukiy2010:Updator:-SNAPSHOT'
}
```
# 使用方法
1. 在使用之前初始化，比如在Application中：
```Java
@Override
public void onCreate() {
  super.onCreate();
  Updator.init("1.1.2.0", //当前版本号
  "https://raw.githubusercontent.com/ukiy2010/testJson/master/README.md");//接口地址
}
```
2. 使用默认的回调检查更新并下载安装。
```Java
Updator.start(mContext);
```
或者你也可以分开执行检查更新和下载更新，并且自定义它们的回调
```
Updator.getUpdateInfo(mContext, mUpdateCallback);//检查更新
Updator.downloadUpdateInfo(mContext, mDownloadCallback);//下载更新
```
# json格式要求
所调用的接口返回json格式如下
```JavaScript
{
  "success": true,//返回信息成功
  "info": "success",//返回结果说明信息
  "data": {
    "new_version": "1.2.1.0",//最新版本号
    "min_version": "1.1.1.0",//最低版本号
    "change_log": "1、修复若干bug 2、流畅性增加 3、多重新能提升",//更新日志
    "url": "http://xxx.com/xxx.apk"//安装包地址
  }
}
```
# 得到json数据后的动作
假设cur为当前版本号，min为最低版本号，new为最新版本号，当cur处于以下区间时的动作
0-----1(min)----2(new)
switch(cur){
    case [0,1): onMustUpdate(); break;//强制更新
    case [1,2): onOptUpdate(); break;//选择更新
    case 2:     onAlreadyNewest(); break;//已经是最新
}
#下一版本计划
1. 避免多次重复请求
2. 增加失败回调
# 更新日志
##v1.1
1. 完善SimpleDownloadCallback强制下载逻辑。
2. 为SimpleUpdateCallback的构造函数添加了选项，可控制是否弹出已经是新版本的提示。
3. 暴露检查更新和下载安装更新的回调。
4. 增加回调中的onFail()方法占位，暂无实现。