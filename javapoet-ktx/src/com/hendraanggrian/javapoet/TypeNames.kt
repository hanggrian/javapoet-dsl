package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass

/** Returns a type name equivalent to [mirror]. */
fun typeNameOf(mirror: TypeMirror): TypeName =
    TypeName.get(mirror)

/** Returns a type name equivalent to [type]. */
fun typeNameOf(type: Class<*>): TypeName =
    TypeName.get(type)

/** Returns a type name equivalent to [type]. */
fun typeNameOf(type: KClass<*>): TypeName =
    typeNameOf(type.java)

/** Returns a type name equivalent to [T]. */
inline fun <reified T> typeNameOf(): TypeName =
    typeNameOf(T::class)
