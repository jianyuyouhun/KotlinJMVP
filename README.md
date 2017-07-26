## 关于 ##

　　此项目是JMVP的Kotlin版本，目的在于学习kotlin语法的应用。

### [JMVP地址在此](https://github.com/jianyuyouhun/JMVP) ###

## README ##

　　完成了JMVP基本的MVP架构的kotlin实现。

## 重点记录 ##

　　重要修饰符含义需要注意：open， companion object{}， @Synchronized

  
1. `open`:在非abstract标记下使用，仅用于方法的标识（对对象无效），标识该方法可被重写。kotlin默认情况下所有fun都是可被调用的的，类似于`public`，但是无法被重写，对比java中方法的`final`标记。如果要让一个方法既能被调用又能被重写，则加上open，如果不想某个方法被外部访问，那么加上private标记
2. `object`：用于声明对象，在单例模式、匿名对象中皆有运用
3. `companion object{}`：伴生对象，对比java中的static，可以包含对象和方法
4. `@Synchronized`：同步修饰符，和java的`synchronized`一样
4. `in`：泛型中的运用，作用是表示该泛型是对内有效作为一个**消费品**，比如声明一个`class A<in T>`，那么内部可以拥有方法`fun setA(a: T) {}`，这里外界决定到来的对象类型只能在内部消费使用
5. `out`：泛型中的运用，作用是表示该泛型是对外的返回结果，是个**生产品**，比如针对`class A<out T>`，可以拥有`fun getA(): T {}`。具体一点，比如我们拥有一个泛型接口A如下：
	
		public interface A<T extends Activity> {
	        T getA();
	    }

	在java中是这么用的。

		public void a(A<BaseActivity> a){
        	A<? extends Activity> a1 = a;
    	}

	而在kotlin中是这样定义和使用的

		interface A<out T: Activity> {
			fun getA(): T
		}

	使用

		fun a(a: A<BaseActivity>) {
			var a1: A<Activity> = a
		}

> 事实上，在Java中，如果我们在声明一个A的时候不使用`? extends Activity`的话，那么我们只能使用具体的类型，否则编译阶段就无法通过，而使用具体类型的话泛型就失去了意义

> 而在Kotlin中，如果不加上out，那么也是一样的结果，要么明确声明类型，要么就编译报错


#### 其他 ####

　　kotlin中很多注解使用都是加上一个@然后作为修饰符在使用

　　当对象为泛型而其夫类也要声明泛型的时候，对其的引用使用需要注意：

	abstract class BaseMVPActivity<MajorPresenter: BaseKTPresenter<*, *>, MajorModel: BaseKTModel>: BaseActivity() {}

	//BaseKTPresenter本身也需要泛型定义，而这里未知，所以传递*（正确与否尚未验证，目前是编译没错）

　　对比java中的使用

	public abstract class BaseMVPActivity<MajorPresenter extends BaseJPresenter, MajorModel extends BaseJModel> extends BaseActivity {}


