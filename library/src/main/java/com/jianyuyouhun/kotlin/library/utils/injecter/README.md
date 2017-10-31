## Kotlin下的运行时注解绑定 ##

### 使用方式 ###


1. ViewInjector.inject(activity/dialog)
2. ViewInjector.inject(viewHolder, view)

### 备注 ###

特别注意kotlin中使用反射机制时的[一些坑](https://github.com/jianyuyouhun/KotlinJMVP#javaclass-和-classjava)

其实kotlin有提供更好获取view的插件，比如[kotterKnife](https://github.com/JakeWharton/kotterknife)，本项目仅用于学习理解java的反射机制在kotlin中的实现