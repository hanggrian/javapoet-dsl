package com.hendraanggrian.javapoet

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.TypeName
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Type
import javax.lang.model.type.ArrayType
import kotlin.reflect.KClass

/** Returns an [ArrayTypeName] equivalent to [TypeName]. */
inline fun TypeName.toArrayTypeName(): ArrayTypeName = ArrayTypeName.of(this)

/** Returns an [ArrayTypeName] equivalent to [Type]. */
inline fun Type.toArrayTypeName(): ArrayTypeName = ArrayTypeName.of(this)

/** Returns an [ArrayTypeName] equivalent to [KClass]. */
inline fun KClass<*>.toArrayTypeName(): ArrayTypeName = ArrayTypeName.of(java)

/** Returns an [ArrayTypeName] equivalent to [T]. */
inline fun <reified T> arrayTypeNameOf(): ArrayTypeName = ArrayTypeName.of(T::class.java)

/** Returns an [ArrayTypeName] equivalent to [ArrayType]. */
inline fun ArrayType.asArrayTypeName(): ArrayTypeName = ArrayTypeName.get(this)

/** Returns an [ArrayTypeName] equivalent to [GenericArrayType]. */
inline fun GenericArrayType.asArrayTypeName(): ArrayTypeName = ArrayTypeName.get(this)
