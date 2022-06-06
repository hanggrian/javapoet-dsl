package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.getParameterizedTypeName
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a [ParameterizedTypeName] applying [TypeName] arguments to [ClassName]. */
inline fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/** Returns a [ParameterizedTypeName] applying [Type] arguments to [Class]. */
inline fun Class<*>.parameterizedBy(vararg typeArguments: Type): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/** Returns a [ParameterizedTypeName] applying [KClass] arguments to [KClass]. */
fun KClass<*>.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.get(java, *typeArguments.toJavaClasses())

/** Returns a [ParameterizedTypeName] applying [TypeName] argument to collection of [ClassName]. */
fun ClassName.parameterizedBy(typeArguments: List<TypeName>): ParameterizedTypeName =
    getParameterizedTypeName(this, typeArguments)

/** Returns a [ParameterizedTypeName] applying [Type] argument to collection of [Class]. */
fun Class<*>.parameterizedBy(typeArguments: Iterable<Type>): ParameterizedTypeName =
    getParameterizedTypeName(asClassName(), typeArguments.map { it.asTypeName() })

/** Returns a [ParameterizedTypeName] applying [KClass] argument to collection of [KClass]. */
fun KClass<*>.parameterizedBy(typeArguments: Iterable<KClass<*>>): ParameterizedTypeName =
    getParameterizedTypeName(asClassName(), typeArguments.map { it.asTypeName() })

/** Returns a [ParameterizedTypeName] applying [T] argument to [ClassName]. */
inline fun <reified T> ClassName.plusParameter(): ParameterizedTypeName =
    parameterizedBy(T::class.asClassName())

/** Returns a [ParameterizedTypeName] applying [T] argument to [Class]. */
inline fun <reified T> Class<*>.plusParameter(): ParameterizedTypeName =
    parameterizedBy(T::class.java)

/** Returns a [ParameterizedTypeName] applying [T] argument to [KClass]. */
inline fun <reified T> KClass<*>.plusParameter(): ParameterizedTypeName =
    parameterizedBy(T::class)

/** Returns a [ParameterizedTypeName] equivalent to [ParameterizedType]. */
inline fun ParameterizedType.asParameterizedTypeName(): ParameterizedTypeName = ParameterizedTypeName.get(this)
