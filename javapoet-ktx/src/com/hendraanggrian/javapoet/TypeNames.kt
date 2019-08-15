package com.hendraanggrian.javapoet

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import com.squareup.javapoet.WildcardTypeName
import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import javax.lang.model.element.TypeElement
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.type.ArrayType
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVariable
import javax.lang.model.type.WildcardType
import kotlin.reflect.KClass

fun TypeMirror.toTypeName(): TypeName =
    TypeName.get(this)

fun KClass<*>.toTypeName(): TypeName =
    TypeName.get(java)

fun TypeName.toArrayTypeName(): ArrayTypeName =
    ArrayTypeName.of(this)

fun KClass<*>.toArrayTypeName(): ArrayTypeName =
    ArrayTypeName.of(java)

fun ArrayType.toArrayTypeName(): ArrayTypeName =
    ArrayTypeName.get(this)

fun GenericArrayType.toArrayTypeName(): ArrayTypeName =
    ArrayTypeName.get(this)

fun KClass<*>.toClassName(): ClassName =
    ClassName.get(java)

fun String.toClassName(): ClassName =
    ClassName.bestGuess(this)

fun String.toClassName(simpleName: String, vararg simpleNames: String): ClassName =
    ClassName.get(this, simpleName, *simpleNames)

fun TypeElement.toClassName(): ClassName =
    ClassName.get(this)

fun ClassName.toParameterizedTypeName(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

fun KClass<*>.toParameterizedTypeName(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.get(java, *typeArguments.mapJava())

fun ParameterizedType.toParameterizedTypeName(): ParameterizedTypeName =
    ParameterizedTypeName.get(this)

fun String.toTypeVariableName(): TypeVariableName =
    TypeVariableName.get(this)

fun String.toTypeVariableName(vararg bounds: TypeName): TypeVariableName =
    TypeVariableName.get(this, *bounds)

fun String.toTypeVariableName(vararg bounds: KClass<*>): TypeVariableName =
    TypeVariableName.get(this, *bounds.mapJava())

fun TypeVariable.toTypeVariableName(): TypeVariableName =
    TypeVariableName.get(this)

fun TypeParameterElement.toTypeVariableName(): TypeVariableName =
    TypeVariableName.get(this)

fun java.lang.reflect.TypeVariable<*>.toTypeVariableName(): TypeVariableName =
    TypeVariableName.get(this)

fun TypeName.toSubtypeWildcardTypeName(): WildcardTypeName =
    WildcardTypeName.subtypeOf(this)

fun KClass<*>.toSubtypeWildcardTypeName(): WildcardTypeName =
    WildcardTypeName.subtypeOf(java)

fun TypeName.toSupertypeWildcardTypeName(): WildcardTypeName =
    WildcardTypeName.supertypeOf(this)

fun KClass<*>.toSupertypeWildcardTypeName(): WildcardTypeName =
    WildcardTypeName.supertypeOf(java)

fun WildcardType.toWildcardTypeName(): TypeName =
    WildcardTypeName.get(this)

fun java.lang.reflect.WildcardType.toWildcardTypeName(): TypeName =
    WildcardTypeName.get(this)
