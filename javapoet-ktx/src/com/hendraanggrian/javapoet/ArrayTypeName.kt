package com.hendraanggrian.javapoet

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.TypeName
import java.lang.reflect.GenericArrayType
import javax.lang.model.type.ArrayType
import kotlin.reflect.KClass

/**
 * Returns an [ArrayTypeName] equivalent to [T].
 * @see typeNameOf
 */
inline fun <reified T> arrayTypeNameOf(): ArrayTypeName = T::class.arrayOf()

/** Returns an [ArrayTypeName] equivalent to [TypeName]. */
fun TypeName.arrayOf(): ArrayTypeName = ArrayTypeName.of(this)

/** Returns an [ArrayTypeName] equivalent to [Class]. */
fun Class<*>.arrayOf(): ArrayTypeName = ArrayTypeName.of(this)

/** Returns an [ArrayTypeName] equivalent to [KClass]. */
fun KClass<*>.arrayOf(): ArrayTypeName = ArrayTypeName.of(java)

/** Returns an [ArrayTypeName] equivalent to [ArrayType]. */
fun ArrayType.arrayOf(): ArrayTypeName = ArrayTypeName.get(this)

/** Returns an [ArrayTypeName] equivalent to [GenericArrayType]. */
fun GenericArrayType.arrayOf(): ArrayTypeName = ArrayTypeName.get(this)
