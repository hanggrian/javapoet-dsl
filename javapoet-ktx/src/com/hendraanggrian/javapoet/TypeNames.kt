package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass

val VOID: TypeName = TypeName.VOID
val BOOLEAN: TypeName = TypeName.BOOLEAN
val BYTE: TypeName = TypeName.BYTE
val SHORT: TypeName = TypeName.SHORT
val INT: TypeName = TypeName.INT
val LONG: TypeName = TypeName.LONG
val CHAR: TypeName = TypeName.CHAR
val FLOAT: TypeName = TypeName.FLOAT
val DOUBLE: TypeName = TypeName.DOUBLE
val OBJECT: TypeName = TypeName.OBJECT

/** Returns a [TypeName] equivalent to this [TypeMirror]. */
fun TypeMirror.asTypeName(): TypeName =
    TypeName.get(this)

/** Returns a [TypeName] equivalent to this [Type]. */
fun Type.asTypeName(): TypeName =
    TypeName.get(this)

/** Returns a [TypeName] equivalent to this [KClass].  */
fun KClass<*>.asTypeName(): TypeName =
    java.asTypeName()

/** Returns a [TypeName] equivalent to this [T].  */
inline fun <reified T> asTypeName(): TypeName =
    T::class.asTypeName()
