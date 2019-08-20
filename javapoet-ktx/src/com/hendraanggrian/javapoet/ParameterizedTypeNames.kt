package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

/** Returns a parameterized type, applying [typeArguments] to [rawType]. */
fun parameterizedTypeNameOf(rawType: ClassName, vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.get(rawType, *typeArguments)

/** Returns a parameterized type, applying [typeArguments] to [rawType]. */
fun parameterizedTypeNameOf(rawType: KClass<*>, vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.get(rawType.java, *typeArguments.toJavaClasses())

/** Returns a parameterized type equivalent to [type]. */
fun parameterizedTypeNameOf(type: ParameterizedType): ParameterizedTypeName =
    ParameterizedTypeName.get(type)
