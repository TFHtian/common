package com.tian.lib_common.lib_ext

import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * 获取当前类绑定的泛型ViewModel-clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

/**
 * 获取泛型VM对应的类
 */
@Suppress("UNCHECKED_CAST")
fun<VM> getVMClass(obj: Any): Class<VM> {
    var cls: Class<*>? = obj.javaClass
    var vmClass: Class<VM>? = null
    while (vmClass == null && cls != null) {
        vmClass = getVMClass(cls)
        cls = cls.superclass
    }
    if (vmClass == null) {
        vmClass = BaseViewModel::class.java as Class<VM>
    }
    return vmClass
}

/**
 * 根据传入的 cls 获取泛型VM对应的类
 */
@Suppress("UNCHECKED_CAST")
fun<VM> getVMClass(cls: Class<*>): Class<VM>? {
    val type = cls.genericSuperclass
    if (type is ParameterizedType) {
        val types = type.actualTypeArguments
        for (t in types) {
            if (t is Class<*>) {
                if (BaseViewModel::class.java.isAssignableFrom(t)) {
                    return t as? Class<VM>
                }
            } else if (t is ParameterizedType) {
                val rawType = t.rawType
                if (rawType is Class<*>) {
                    if (BaseViewModel::class.java.isAssignableFrom(rawType)) {
                        return rawType as? Class<VM>
                    }
                }
            }
        }
    }
    return null
}



