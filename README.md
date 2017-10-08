## 基于MVP的通用快速开发的基础框架，包含功能点：

+ MVP开发模式，RxJava组件栈+DataManager，参考架构 [boilerplate](https://github.com/ribot/android-boilerplate)

+ 模版模式封装的BaseActivity和BaseFragment，清晰处理界面数据加载流程

+ 网络访问Retrofit2+OKhttp3的封装类HttpHelper,RxLifecicycle统一网络访问/任务处理生命周期控制，activity,fragment销毁时取消订阅

+ 图片加载统一封装，支持不同图片加载库（Glide、Picasso）的切换，Wifi下加载图片等功能

+ 数据库数据保存，迁移框架GreenDao封装

+ 通用工具utils包，包含日志，文件、时间，数据加解密，权限处理等处理类

+ 通用视图widget包，recyclerview、commonAdapter、dialog、popupwindow等处理类
