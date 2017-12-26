# 用来进行各种效果测试的项目

##### 1 仿知乎滑动广告效果

![知乎滑动广告.gif](http://upload-images.jianshu.io/upload_images/3515789-7f1f9a137e12c4dc.gif?imageMogr2/auto-orient/strip)

感觉粗糙，不满意

---

##### 2 LruCache的实例

![LruCache的实例](https://i.loli.net/2017/11/16/5a0d4ae469fd6.jpg)

##### 3 图片压缩的简单例子

[图片加载原理](http://www.jianshu.com/p/94e37c901107)

##### 4 hencoder动画模仿

![](http://upload-images.jianshu.io/upload_images/3515789-5a7325483dc7ceb1.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

关键的地方：其实就是camera的y轴旋转45度之后然后转动画布canvas，但是最后画布还要转回来，因为不是整一个画布在旋转。。


1. 旋转画布
2. 先用camera旋转y轴，
3. 旋转画布（还原原画布位置，不转回来的话那么就变成了整个bitmap在转了）

