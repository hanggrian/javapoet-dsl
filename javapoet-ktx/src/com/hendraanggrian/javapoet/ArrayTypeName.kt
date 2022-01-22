package com.hendraanggrian.javapoet

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Type
import javax.lang.model.type.ArrayType
import kotlin.reflect.KClass

/** Returns a [ClassName] created from the given parts and convert it to [ArrayTypeName]. */
fun String.arrayOf(simpleName: String, vararg simpleNames: String): ArrayTypeName =
    classOf(simpleName, *simpleNames).arrayOf()

/** Returns an [ArrayTypeName] equivalent to [TypeName]. */
inline fun TypeName.arrayOf(): ArrayTypeName = ArrayTypeName.of(this)

/** Returns an [ArrayTypeName] equivalent to [Class]. */
inline fun Type.arrayOf(): ArrayTypeName = ArrayTypeName.of(this)

/** Returns an [ArrayTypeName] equivalent to [KClass]. */
inline fun KClass<*>.arrayOf(): ArrayTypeName = ArrayTypeName.of(java)

/** Returns an [ArrayTypeName] equivalent to [ArrayType]. */
inline fun ArrayType.asArrayTypeName(): ArrayTypeName = ArrayTypeName.get(this)

/** Returns an [ArrayTypeName] equivalent to [GenericArrayType]. */
inline fun GenericArrayType.asArrayTypeName(): ArrayTypeName = ArrayTypeName.get(this)
