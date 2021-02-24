# 健康运动APP项目安卓端文档



## Overview

本文档旨在通过写清楚使用的技术和参考资料，联通各成员，打通壁垒，共同进步。
[toc]

---



## Layout设计

Layout不是做得和效果图一样就代表可以了，能用了，一个能用的layout是**需要考虑页面功能需求的。**

例如一个以卡片形式展示不同商品的页面，编写Layout文件时如果只是像画画一样把效果图搬上去放到指定的位置的话，是没有太多作用的，只是又一个效果图罢了，只能看不能用。而如果使用`ListView`或`RecyclerView`这类专门展示列表信息的控件的话，则可以节省大量开发时间，编写Java代码时无需过多修改。

<u>请合理利用各类控件及其特性。</u>，建议充分了解各种控件的作用和特性，一篇较为详细的Android入门学习教程：[Android基础入门教程 - RUNOOB](https://www.runoob.com/w3cnote/android-tutorial-intro.html)



### 基本布局技巧与建议

#### 谨慎使用`layout_margin`进行布局

在此前的许多布局中，发现存在这样的问题：一个页面中绝大部分控件的相对位置靠`layout_margin`属性来维持，例如一个标题栏要设计成这样：

![image-20210224214344409](C:%5CUsers%5C13651%5CDesktop%5C%E5%81%A5%E5%BA%B7%E8%BF%90%E5%8A%A8APP%E9%A1%B9%E7%9B%AE%E5%AE%89%E5%8D%93%E7%AB%AF%E6%96%87%E6%A1%A3.assets%5Cimage-20210224214344409.png)

“消息”文字为一个`TextView`，右侧两个图标为`ImageView`，都放在以一个横向的`LinerLayout`里，文字与图标之间的间隙是一段空白。

如果`TextView`和`ImageView`直接放在`LinerLayout`里，会默认全部靠左对齐，如图：

![image-20210224214307788](C:%5CUsers%5C13651%5CDesktop%5C%E5%81%A5%E5%BA%B7%E8%BF%90%E5%8A%A8APP%E9%A1%B9%E7%9B%AE%E5%AE%89%E5%8D%93%E7%AB%AF%E6%96%87%E6%A1%A3.assets%5Cimage-20210224214307788.png)

而如果修改“消息”`TextView`的`layout_marginRight`为`xxxdp`，中间的空白就出来了。这样做行不行呢？当然行，因为有效果了，但是只是在当前尺寸的屏幕下可行，到了其它尺寸的屏幕上就会出现控件位置不对的致命错误，这种方法需要对每个尺寸的屏幕都适配一个layout，这是吃力不讨好的。


| ![image-20210224214716513](C:%5CUsers%5C13651%5CDesktop%5C%E5%81%A5%E5%BA%B7%E8%BF%90%E5%8A%A8APP%E9%A1%B9%E7%9B%AE%E5%AE%89%E5%8D%93%E7%AB%AF%E6%96%87%E6%A1%A3.assets%5Cimage-20210224214716513.png) | ![image-20210224215726810](C:%5CUsers%5C13651%5CDesktop%5C%E5%81%A5%E5%BA%B7%E8%BF%90%E5%8A%A8APP%E9%A1%B9%E7%9B%AE%E5%AE%89%E5%8D%93%E7%AB%AF%E6%96%87%E6%A1%A3.assets%5Cimage-20210224215726810.png) |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| “消息”`TextView`的`layout_marginRight`为230dp时在1080x1920,420dpi屏幕上的预览，正常 | “消息”`TextView`的`layout_marginRight`为230dp时在1080x2160,440dpi屏幕上的预览，文字显示不全，显示“√”的`ImageView`显然也已经超出屏幕范围了， |

而比较推荐的接近方法是使用`layout_weight`属性，使控件具有自动拉伸的特性。值得注意的是，“消息”`TextView`的宽被拉伸以后没有文字显示的区域也是可以被点击的，当控件具有点击事件的时候建议填充以一个`Space`控件并赋值`layout_weight`来进行占位，这样就可以把两边的控件“顶住”，又不会存在点击事件触发区域的问题。

#### 多级`LinearLayout`时不妨试试`Tablelayout`,`GridLayout`等



#### 控件背景多用`xml`资源，少用图片

要实现一个圆角按钮，你可能会想到使用一张有色圆角图片作为背景实现如下效果：

![image-20210224221425208](C:%5CUsers%5C13651%5CDesktop%5C%E5%81%A5%E5%BA%B7%E8%BF%90%E5%8A%A8APP%E9%A1%B9%E7%9B%AE%E5%AE%89%E5%8D%93%E7%AB%AF%E6%96%87%E6%A1%A3.assets%5Cimage-20210224221425208.png)



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

#### 使用RecyclerView

我们的项目经常会有很多同类型的信息需要以列表、瀑布流等形式进行展示，如下图红框圈出。在布局的时候可不能放几个图在哪就完事，我们需要用到`RecyclerView`控件。

![image-20210224225836043](C:%5CUsers%5C13651%5CDesktop%5C%E5%81%A5%E5%BA%B7%E8%BF%90%E5%8A%A8APP%E9%A1%B9%E7%9B%AE%E5%AE%89%E5%8D%93%E7%AB%AF%E6%96%87%E6%A1%A3.assets%5Cimage-20210224225836043.png)



`RecyclerView`是一个比`ListView`更加强大的控件，非常适合用来展示信息。在写Layout时想要预览展示效果，可以关注一下`RecyclerView`的`listitem`和`listCount`属性。

推荐学习：

1. [RecyclerView详解 - 简书](https://www.jianshu.com/p/b4bb52cdbeb7)
2. [Android RecyclerView从入门到玩坏 - imooc](https://www.imooc.com/article/68704)



## Java编写

### 使用RecyclerViewAdapter

RecyclerView控件需要使用adapter来装载内容，项目建议统一使用第三方库 [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)，此适配器可以简化编码，实现多Item布局等等功能。推荐阅读 [开源框架BaseRecyclerViewAdapterHelper使用——RecyclerView万能适配器 - 简书](https://www.jianshu.com/p/1e20f301272e)



#### 多布局适配器MultipleItemQuickAdapter

这是我们项目里的多布局适配器类名，传入的item类为`MultipleItem`。



#### 多布局实体类MultipleItem

这是我们项目里的多布局实体类名，相当于一个“中介”，需要展示新的实体类时需要在此类中进行相应修改，具体细节参考上面的文章。



### 视频缓存与管理VideoCacheUtil

由于没有找到合适的第三方库，我们编写自己的视频缓存管理工具，类名为`VideoCacheUtil`。

简单使用：

```java
VideoCacheUtil.with(context)
				.load(vidoeUrl)
				.downloader(downloadActivity)
				.intoUri(uri)
```



#### 类方法

##### isExistInLocal(String url)

参数：String url

返回类型：Boolean

功能：判断传入的视频Url是否已经缓存到本地并返回真值



##### isExistInLocal(ArrayList\<String> Url) //未上线

参数：ArrayList\<String> url

返回类型：ArrayList\<Boolean> 

功能：判断传入的视频url序列是否已经缓存到本地并返回对应真值List



##### getCachedVideoList(void)//未上线

参数：void

返回类型：List\<CachedVideo>

功能：返回已经缓存到本地的视频实体类CachedVideo的List







 

