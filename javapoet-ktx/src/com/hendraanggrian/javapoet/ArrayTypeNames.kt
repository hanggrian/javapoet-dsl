package com.hendraanggrian.javapoet

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.TypeName
import java.lang.reflect.GenericArrayType
import javax.lang.model.type.ArrayType
import kotlin.reflect.KClass

/** Returns an [ArrayTypeName] equivalent to this [TypeName]. */
fun TypeName.asArrayTypeName(): ArrayTypeName = ArrayTypeName.of(this)

/** Returns an [ArrayTypeName] equivalent to this [Class]. */
fun Class<*>.asArrayTypeName(): ArrayTypeName = ArrayTypeName.of(this)

/** Returns an [ArrayTypeName] equivalent to this [KClass]. */
fun KClass<*>.asArrayTypeName(): ArrayTypeName = java.asArrayTypeName()

/** Returns an [ArrayTypeName] equivalent to this [T]. */
inline fun <reified T> asArrayTypeName(): ArrayTypeName = T::class.asArrayTypeName()

/** Returns an [ArrayTypeName] equivalent to this [ArrayType]. */
fun ArrayType.asArrayTypeName(): ArrayTypeName = ArrayTypeName.get(this)

/** Returns an [ArrayTypeName] equivalent to this [GenericArrayType]. */
fun GenericArrayType.asArrayTypeName(): ArrayTypeName = ArrayTypeName.get(this)
