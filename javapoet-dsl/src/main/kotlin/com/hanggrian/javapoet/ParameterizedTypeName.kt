@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

/** Returns a parameterized type, applying typeArguments to rawType. */
public inline fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/** Returns a parameterized type, applying typeArguments to rawType. */
public fun ClassName.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments.map { it.name }.toTypedArray())

/** Returns a parameterized type, applying typeArguments to rawType. */
public inline fun <reified T> ClassName.parameterizedBy(): ParameterizedTypeName =
    parameterizedBy(T::class.name)
