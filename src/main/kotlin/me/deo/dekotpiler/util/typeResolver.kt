package me.deo.dekotpiler.util

import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

fun resolveTypeParameter(clazz: KClass<*>, target: KClass<*>, name: String) =
    (clazz.java.genericInterfaces + clazz.java.genericSuperclass)
        .filterIsInstance<ParameterizedType>()
        .find { it.rawType == target.java }
        ?.let { type ->
            val offset = target.java.typeParameters.indexOfFirst { it.name == name }
            (type.actualTypeArguments[offset] as Class<*>).kotlin
        }