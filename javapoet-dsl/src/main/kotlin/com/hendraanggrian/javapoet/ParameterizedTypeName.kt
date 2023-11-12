@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

/** Returns a parameterized type, applying typeArguments to rawType. */
inline fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/** Returns a parameterized type, applying typeArguments to rawType. */
fun ClassName.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments.map { it.name }.toTypedArray())

/** Returns a parameterized type, applying typeArguments to rawType. */
inline fun <reified T> ClassName.parameterizedBy(): ParameterizedTypeName =
    parameterizedBy(T::class.name)
