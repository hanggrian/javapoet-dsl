package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a parameterized type, applying `typeArguments` to `this`.  */
fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/** Returns a parameterized type, applying `typeArguments` to `this`.  */
fun Class<*>.parameterizedBy(vararg typeArguments: Type): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/** Returns a parameterized type, applying `typeArguments` to `this`.  */
fun KClass<*>.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    java.parameterizedBy(*typeArguments.toJavaClasses())

/** Returns a [ParameterizedTypeName] equivalent to this [ParameterizedType].  */
fun ParameterizedType.asParameterizedTypeName(): ParameterizedTypeName =
    ParameterizedTypeName.get(this)
