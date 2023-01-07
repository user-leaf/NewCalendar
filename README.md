#### 日历控件  
---
[自定义实现日历控件](http://www.imooc.com/video/13642)  
循序渐进学习自定义一个控件的分析制作流程。

#### 核心知识点
继承系统控件 - 比如继承ImageView，子类增加一个点击方法，暴露出一些事件给别人处理  
组合系统控件  
自定义绘制控件 - 通过重载onDraw()方法

添加Attribute  
添加事件  

涉及到了：分析日历Layout及声明，实现其内部的细节业务逻辑。

#### 知识总结：
1. 在res/values文件夹下建立xml，如attrs.xml（命名随意）。
2. 像android:layout_width和android:padding这种以android开头的属性是系统自带的，还可以添加自定义属性。
3. getInstance()方法在工程里api要求24。
