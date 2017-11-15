package com.jianyuyouhun.kotlin.library.utils.injecter

import com.jianyuyouhun.kotlin.library.app.KTApp
import com.jianyuyouhun.kotlin.library.mvp.BaseKTModel
import java.lang.reflect.Modifier

/**
 * 注解绑定model
 * Created by wangyu on 2017/11/15.
 */
fun injectModel(any: Any) {
    var cls: Class<*> = any::class.java
    while (cls != BaseKTModel::class.java && cls != Any::class.java) {
        val declaredFields = cls.declaredFields ?: continue
        if (declaredFields.isEmpty()) continue
        for (field in declaredFields) {
            val modifiers = field.modifiers
            if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) continue

            if (field.isAnnotationPresent(Model::class.java)) continue

            val type = field.type
            if (!BaseKTModel::class.java.isAssignableFrom(type)) {
                throw ModelInjectException("@Model 只能在BaseJModel子类中使用")
            }

            @Suppress("UNCHECKED_CAST")
            val baseModel = KTApp.mInstance.getKTModel(type as Class<out BaseKTModel>) ?: throw ModelInjectException(type.simpleName + "Model 还未初始化")

            if (!field.isAccessible) field.isAccessible = true

            try {
                field.set(any, baseModel)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
        cls = cls::class.java.superclass
    }
}

class ModelInjectException(msg: String) : RuntimeException(msg)
