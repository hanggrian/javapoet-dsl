package com.hendraanggrian.javapoet

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.TypeName
import java.lang.reflect.GenericArrayType
import javax.lang.model.type.ArrayType
import kotlin.reflect.KClass

/** Returns an array type whose elements are all instances of [componentType]. */
fun arrayTypeNameOf(componentType: TypeName): ArrayTypeName =
    ArrayTypeName.of(componentType)

/** Returns an array type whose elements are all instances of [componentType]. */
fun arrayTypeNameOf(componentType: KClass<*>): ArrayTypeName =
    ArrayTypeName.of(componentType.java)

/** Returns an array type whose elements are all instances of [T]. */
inline fun <reified T> arrayTypeNameOf(): ArrayTypeName =
    arrayTypeNameOf(T::class)

/** Returns an array type equivalent to [mirror]. */
fun arrayTypeNameOf(mirror: ArrayType): ArrayTypeName =
    ArrayTypeName.get(mirror)

/** Returns an array type equivalent to [type]. */
fun arrayTypeNameOf(type: GenericArrayType): ArrayTypeName =
    ArrayTypeName.get(type)
