## 关于 ##

　　此项目是JMVP的Kotlin版本，目的在于学习kotlin语法的应用。

### [JMVP地址在此](https://github.com/jianyuyouhun/JMVP) ###

## README ##

　　完成了JMVP基本的MVP架构的kotlin实现。

## 重点记录 ##

　　重要修饰符含义需要注意：open， companion object{}， @Synchronized

　　泛型的使用中，out修饰符的意义

　　当对象为abstract且包含泛型的时候，对其的引用使用需要注意：

	MajorPresenter: BaseKTPresenter<*, *>
	//BaseKTPresenter本身也需要泛型定义，而这里未知，所以传递*（正确与否尚未验证，目前是编译没错）
