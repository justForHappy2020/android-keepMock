# 健康运动APP项目安卓端文档



## Overview

本文档旨在通过写清楚使用的技术和参考资料，联通各成员，打通壁垒，共同进步。

[toc]

---

## 命名规范

请严格参考《阿里巴巴Android开发手册》的命名规范。

module：注册(register)、首页(homepage)、运动中心(sport)、社区(community)、饮食健康(diet)、我的(me)

用例：

1. 开始训练页Activity  `sport_activity_train_start`
2. 课程完成页Activity  `sport_activity_train_compelete`

变量名、Id资源名原则上均以驼峰命名法命名，用例：

​	**变量**：shareList, currentPage

​	**Id**: tvUserName, btnFollow



## UI与布局

Layout不是做得和效果图一样就代表可以了，能用了，一个能用的layout是**需要考虑页面功能需求的。**

例如一个以卡片形式展示不同商品的页面，编写Layout文件时如果只是像画画一样把效果图搬上去放到指定的位置的话，是没有太多作用的，只是又一个效果图罢了，只能看不能用。而如果使用`ListView`或`RecyclerView`这类专门展示列表信息的控件的话，则可以节省大量开发时间，编写Java代码时无需过多修改。

<u>请合理利用各类控件及其特性。</u>一篇较为详细的Android入门学习教程：[Android基础入门教程 - RUNOOB](https://www.runoob.com/w3cnote/android-tutorial-intro.html)



### 基本布局技巧与建议

针对以往问题的一些建议。

#### 控件留白，居中居左居右等

谨慎使用`layout_margin`进行布局

在此前的许多布局中，发现存在这样的问题：一个页面中绝大部分控件的相对位置靠`layout_margin`属性来维持，例如一个标题栏要设计成这样：

![image-20210224214344409](https://i.loli.net/2021/02/25/bFpjOYvI6uMzT5a.png)

“消息”文字为一个`TextView`，右侧两个图标为`ImageView`，都放在以一个横向的`LinerLayout`里，文字与图标之间的间隙是一段空白。

如果`TextView`和`ImageView`直接放在`LinerLayout`里，会默认全部靠左对齐，如图：

![image-20210224214307788](https://i.loli.net/2021/02/25/jYpvbf4NeqxSm6w.png)

而如果修改“消息”`TextView`的`layout_marginRight`为`xxxdp`，中间的空白就出来了。这样做行不行呢？当然行，因为有效果了，但是只是在当前尺寸的屏幕下可行，到了其它尺寸的屏幕上就会出现控件位置不对的致命错误，这种方法需要对每个尺寸的屏幕都适配一个layout，这是吃力不讨好的。


| ![image-20210224214716513](https://i.loli.net/2021/02/25/DJKdVszhv3c9xPo.png) | ![image-20210224215726810](https://i.loli.net/2021/02/25/lcDeLHvSPWT9qpQ.png) |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| “消息”`TextView`的`layout_marginRight`为230dp时在1080x1920,420dpi屏幕上的预览，正常 | “消息”`TextView`的`layout_marginRight`为230dp时在1080x2160,440dpi屏幕上的预览，文字显示不全，显示“√”的`ImageView`显然也已经超出屏幕范围了， |

而比较推荐的接近方法是使用`layout_weight`属性，使控件具有自动拉伸的特性。值得注意的是，“消息”`TextView`的宽被拉伸以后没有文字显示的区域也是可以被点击的，当控件具有点击事件的时候建议填充以一个`Space`控件并赋值`layout_weight`来进行占位，这样就可以把两边的控件“顶住”，又不会存在点击事件触发区域的问题。

#### 复杂页面设计

多级`LinearLayout`时不妨试试`Tablelayout`, `GridLayout`, `ConstrainLayout`等, 尽量减少嵌套，避免过长的绘制时间。



#### 控件设计

**控件背景多用`xml`资源，少用图片。**

要实现一个圆角按钮，你可能会想到使用一张有色圆角图片作为背景实现如下效果：

![image-20210224221425208](https://i.loli.net/2021/02/25/jYUQnl9SkfOPumt.png)



但这是没有必要的，而且图片资源占用空间大，很多不具有矢量性。故而建议使用**xml**的`shape`标签实现[*控件背景不仅仅可以是图片，也可以是以一个xml编写的资源*]，上图所示的背景效果的xml文件如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#80CAC8C8" /><!--填充，可以是纯色也可以是渐变-->
    <stroke android:width="1dip" android:color="#fefefe" /><!--描边，可以是实线虚线等-->
    <corners android:radius="20dp"/><!--圆角弧度-->
    <padding android:bottom="3dp"
        android:left="3dp"
        android:right="3dp"
        android:top="3dp"/>
</shape>
```

xml可以实现的效果很多，有需要可以多多了解。

1. 一篇关于`shape`标签的干货：[Android XML shape 标签使用详解 - CSDN](https://blog.csdn.net/zwname/article/details/82753885)
2. `layer-list`标签实现边框阴影：[Android 使用xml实现边框阴影，背景渐变效果（附有RGB颜色查询对照表）](https://www.cnblogs.com/zhujiabin/p/9375893.html)



### 列表内容展示

项目有大量列表内容需要展示，布局时请先了解如下材料。

#### 使用RecyclerView

我们的项目经常会有很多同类型的信息需要以列表、瀑布流等形式进行展示，如下图红框圈出。在布局的时候可不能放几个图在哪就完事，我们需要用到`RecyclerView`控件。

![image-20210224225836043](https://i.loli.net/2021/02/25/N2IFOuJ6lvKLWdA.png)



`RecyclerView`是一个比`ListView`更加强大的控件，非常适合用来展示信息。在写Layout时想要预览展示效果，可以关注一下`RecyclerView`的`listitem`和`listCount`属性。

推荐学习：

1. [RecyclerView详解 - 简书](https://www.jianshu.com/p/b4bb52cdbeb7)
2. [Android RecyclerView从入门到玩坏 - imooc](https://www.imooc.com/article/68704)



#### Tab栏

实现Tab栏的方式也很多，但一般推荐`TabLayout`与`ViewPager`联动使用。如图，上方可用使用`TabLayout`实现带有指示器(Indicator)的Tabs，下方用`ViewPager`装载`Fragment`。“课程”，“动态”，“好友”，“文章”各栏的内容页面编写单独的Fragment。

![image-20210225074814349](https://i.loli.net/2021/02/25/EpU8wZJyhQCFNAH.png)



## Java编写

Java编写规范请先参考《阿里巴巴Android开发手册》，以下为本项目使用的库或一些技术的细节。

### 使用RecyclerViewAdapter

RecyclerView控件需要使用adapter来装载内容，项目建议统一使用第三方库 [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)，此适配器可以简化编码，实现多Item布局等等功能。推荐阅读 [开源框架BaseRecyclerViewAdapterHelper使用——RecyclerView万能适配器 - 简书](https://www.jianshu.com/p/1e20f301272e)



#### 多布局适配器MultipleItemQuickAdapter

这是我们项目里的多布局适配器类名，传入的item类为`MultipleItem`。



#### 多布局实体类MultipleItem

这是我们项目里的多布局实体类名，相当于一个“中介”，需要展示新的实体类时需要在此类中进行相应修改，具体细节参考上面的文章。



### 视频缓存与管理VideoCacheUtil

由于没有找到合适的第三方库，我们编写自己的视频缓存管理工具，类名为`VideoCacheUtil`。

简单使用：（未上线）

```java
VideoCacheUtil.with(context)
			.load(vidoeUrl)
			.downloader(downloadActivity)
			.intoUri(uri)
```



#### 类方法

| 方法名                                               | 参数                    | 返回类型            | 功能                                                      |
| ---------------------------------------------------- | ----------------------- | ------------------- | --------------------------------------------------------- |
| **isExistInLocal(String url)**                       | String url              | Boolean             | 判断传入的视频url是否已经缓存到本地并返回真值             |
| **isExistInLocal(ArrayList\<String> urls) //未上线** | ArrayList\<String> urls | ArrayList\<Boolean> | 判断传入的视频url序列是否已经缓存到本地并返回对应真值List |
| **getCachedVideoList(void)//未上线**                 | void                    | List\<CachedVideo>  | 返回已经缓存到本地的视频实体类CachedVideo的List           |



## Github操作

代码仓库操作规范。

### 上传

1. 上传自己的代码请上传到自己的分支

2. 为避免主支融合与他人代码出现冲突，大量爆红，每次上传前**先拉取主支代码在本地进行融合**，无误后再传**分支**。

   方法：正确安装git后，在Android Studio的Terminal内输入**git pull origin master **

   参考资料：[git merge冲突解决](
